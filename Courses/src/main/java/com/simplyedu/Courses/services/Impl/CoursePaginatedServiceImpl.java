package com.simplyedu.Courses.services.Impl;
import com.simplyedu.Courses.entities.response.CourseUpdateResponse;
import com.simplyedu.Courses.entities.response.*;
import com.simplyedu.Courses.exceptions.CategoryNotFoundException;
import com.simplyedu.Courses.exceptions.CourseNotFoundException;
import com.simplyedu.Courses.exceptions.DuplicateCourseException;
import com.simplyedu.Courses.exceptions.InvalidSortingStrategyException;
import com.simplyedu.Courses.entities.Category;
import com.simplyedu.Courses.entities.Course;
import com.simplyedu.Courses.repositories.CategoryRepository;
import com.simplyedu.Courses.repositories.ChapterRepository;
import com.simplyedu.Courses.repositories.CoursePaginatedRepository;
import com.simplyedu.Courses.services.CoursePaginatedService;
import com.simplyedu.Courses.utils.CourseMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service("paginatedService")
public class CoursePaginatedServiceImpl implements CoursePaginatedService {

    private CourseMapper courseMapper = new CourseMapper();
    private EntityManager entityManager;
    private final CoursePaginatedRepository paginatedRepository;
    private final CategoryRepository categoryRepository;
    

    public CoursePaginatedServiceImpl(@Qualifier("paginatedRepository") CoursePaginatedRepository paginatedRepository,
                                      EntityManager entityManager,
                                      CategoryRepository categoryRepository) {
        this.paginatedRepository = paginatedRepository;
        this.entityManager = entityManager;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public GetAllResponse getAll(Pageable pageable,
                                 String category,
                                 String searchQuery,
                                 Double minPrice,
                                 Double maxPrice,
                                 String sortBy) {
        
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Course> query = criteriaBuilder.createQuery(Course.class);
        Root<Course> course = query.from(Course.class); //jpa representation of the class

        List<Predicate> filters = new ArrayList<>();
        Order sortPredicate = null;

        if(category != null && !category.isBlank()) {
            Subquery<Category> categorySubquery = query.subquery(Category.class);   
            Root<Course> courseSubroot = categorySubquery.correlate(course);
            Join<Course, Category> join = courseSubroot.join("categories");
            categorySubquery.select(join);
            categorySubquery.where(criteriaBuilder.equal(join.get("name"), category));
            filters.add(criteriaBuilder.exists(categorySubquery));
        }
        if(searchQuery != null && !searchQuery.isBlank()) {
            filters.add(criteriaBuilder.like(criteriaBuilder.upper(course.<String>get("title")),
                    "%" + searchQuery.toUpperCase() + "%"));
        }
        if(minPrice != null && minPrice > 0) {
            filters.add(criteriaBuilder.greaterThan(course.get("price"), minPrice));
        }
        if(maxPrice != null && maxPrice > 0) {
            filters.add(criteriaBuilder.lessThan(course.get("price"), maxPrice));
        }
        if(sortBy != null && !sortBy.isBlank()){
            sortPredicate = switch (sortBy.toUpperCase()){
                case "PRICE_ASCENDING" -> criteriaBuilder.asc(course.get("price"));
                case "PRICE_DESCENDING" -> criteriaBuilder.desc(course.get("price"));
                case "ALPHABETIC" -> criteriaBuilder.asc(course.get("title"));
                case "NEWEST" -> criteriaBuilder.desc(course.get("creationDate"));
                case "OLDEST" -> criteriaBuilder.asc(course.get("creationDate"));
                default -> null;
            };
            if(sortPredicate == null) throw new InvalidSortingStrategyException("Invalid sorting");
        }

        Predicate[] finalPredicates = new Predicate[filters.size()];
        query.where(filters.toArray(finalPredicates));

        if(sortPredicate != null){
            query.orderBy(sortPredicate);
        }

        List<Course> result = entityManager
                .createQuery(query)
                .setMaxResults(pageable.getPageSize())
                .setFirstResult(pageable.getPageNumber())
                .getResultList();
        
        return new GetAllResponse(
                new PageImpl<>(result, pageable, getTotalCount(criteriaBuilder, finalPredicates)));
    }
   
    private Long getTotalCount(CriteriaBuilder builder, Predicate[] filters){
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Course> courseRoot = query.from(Course.class);
        query.where(filters);
        query.select(builder.count(courseRoot));
        return entityManager.createQuery(query).getSingleResult();
    }
    
    @Override
    public CourseByIdResponse getCourseById(Long id) {
        Optional<Course> course = paginatedRepository.findById(id);
        if(course.isEmpty()) 
            throw new CourseNotFoundException("Course not found");
        return courseMapper.courseToCourseByIdResponse(course.get());
    }

    @Override
    public CourseDeleteResponse deleteCourseById(Long id) {
        if(!paginatedRepository.existsById(id))
            throw new CourseNotFoundException("Course not found"); 
        paginatedRepository.deleteById(id);
        return new CourseDeleteResponse("Course deleted");
    }

    @Override
    public CourseUpdateResponse updateCourseById(Course course) {
        if(course == null)
            throw new CourseNotFoundException("Course not found");

        Course courseToBeUpdated = paginatedRepository.getReferenceById(course.getId());

        if(courseToBeUpdated == null) 
            throw new CourseNotFoundException("Course not found");

        if(course.getCategories() != null){
            for(Category category : course.getCategories()){
                if(!categoryRepository.existsByName(category.getName()))
                    categoryRepository.save(category);
                Category categoryNew = categoryRepository.findByName(category.getName());
                category.setId(categoryNew.getId());
            }
        }
        
        paginatedRepository.save(course);
        
        return courseMapper.courseToCourseUpdateResponse(course);
    }

    @Override
    public CourseSaveResponse saveCourse(Course course) {
        if(course == null)
            throw new CourseNotFoundException("Course not found");

        if(paginatedRepository.existsByTitle(course.getTitle()))
            throw new DuplicateCourseException("Course already exists");
        
        if(course.getCategories() != null){
            for(Category category : course.getCategories()){
                if(!categoryRepository.existsByName(category.getName())) 
                    categoryRepository.save(category); 
                Category categoryNew = categoryRepository.findByName(category.getName());
                category.setId(categoryNew.getId());
            }
        }
        
        paginatedRepository.save(course); //won`t return id otherwise

        return courseMapper.courseToCourseSaveResponse(course); 
    }

    @Override
    public CategoryNameResponse getAllCategoryNames() {
        List<String> categories = paginatedRepository.getAllCategoryNames();
        if(categories == null)
            throw new CategoryNotFoundException("No categories found");
        return new CategoryNameResponse(categories);
    }

    @Override
    public CoursesByIdResponse getAllByCourseIds(List<Long> courseIds) {
        if(courseIds == null)
            throw new CourseNotFoundException("Courses not found");
        
        List<Course> courses = paginatedRepository.findAllById(courseIds);
        
        if(courses == null)
            throw new CourseNotFoundException("No courses found");
        
        
        return new CoursesByIdResponse(courses);
    }
    
//    @Override
//    public CourseDeleteResponse addCategoryToCourse(Long courseId, String name){ //todo: remove 
//        CourseDbo course = paginatedRepository.getReferenceById(courseId);
//        if(course == null)
//            throw new CourseNotFoundException("Course not found");
//        CategoryDbo category = categoryRepository.findByName(name.toLowerCase());
//        if(category == null)
//            throw new CategoryNotFoundException("Category not found");
//        if(course.getCategories() == null)
//            course.setCategories(new ArrayList<>());        
//        course.getCategories().add(category);
//        paginatedRepository.save(course);
//        return new CourseDeleteResponse("Category added to course");
//    }
}
