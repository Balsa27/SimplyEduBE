    package com.simplyedu.Statistics.service.Impl;

    import com.simplyedu.Statistics.entities.Purchase;
    import com.simplyedu.Statistics.entities.Response.ComplexPurchaseResponse;
    import com.simplyedu.Statistics.entities.StatisticsDetailed;
    import com.simplyedu.Statistics.entities.Subscription;
    import com.simplyedu.Statistics.http.PurchaseService;
    import com.simplyedu.Statistics.http.SubscriptionService;
    import com.simplyedu.Statistics.entities.Response.StatisticsResponse;
    import com.simplyedu.Statistics.service.StatisticsService;
    import org.springframework.stereotype.Service;

    import java.time.LocalDate;
    import java.util.List;

    @Service
    public class StatisticsServiceImpl implements StatisticsService {

        private final PurchaseService purchaseService;
        private final SubscriptionService subscriptionService;

        public StatisticsServiceImpl(PurchaseService purchaseService, 
                                     SubscriptionService subscriptionService) {
            this.purchaseService = purchaseService;
            this.subscriptionService = subscriptionService;
        }
        
        private long getPercentage(long n, long total) {
            return (n * 100) / total;
        }

        @Override
        public StatisticsResponse getStatistics() {
            String leadingIncomeSource;
            long allSubscriptions = subscriptionService.getSubscriptionStatistics().getAllSubscriptions().size();
            long allPurchases = purchaseService.getDetailedPurchaseStatistics().getAllPurchases().size();
            long total = allSubscriptions + allPurchases;
            
            if(allSubscriptions > allPurchases)
                leadingIncomeSource = "Subscriptions are in the lead by having " + getPercentage(allSubscriptions, total) + "% out of total income";
            else if(allSubscriptions < allPurchases)
                leadingIncomeSource = "Purchases are in the lead by having " + getPercentage(allPurchases, total) + "% out of total income"; 
            else 
                leadingIncomeSource = "Currently Subscriptions and Purchases bring equal income.";
            
            return new StatisticsResponse(
                    subscriptionService
                            .getSubscriptionStatistics()
                            .getAllSubscriptions()
                            .stream()
                            .filter(Subscription::isActive)
                            .count(),
                    subscriptionService
                            .getSubscriptionStatistics()
                            .getAllSubscriptions()
                            .stream()
                            .filter(s -> !s.isActive())
                            .count(),
                    (long) purchaseService.getDetailedPurchaseStatistics().getAllPurchases().size(),
                    leadingIncomeSource
            );
        }

        @Override
        public ComplexPurchaseResponse getDetailedStatistics(StatisticsDetailed statisticsDetailed) {
            List<Purchase> allPurchases = purchaseService.getDetailedPurchaseStatistics().getAllPurchases();
            
            switch (statisticsDetailed.getType()){
                case MONTHLY -> {
                    return new ComplexPurchaseResponse(
                            allPurchases
                                    .stream()
                                    .filter(purchase -> purchase
                                            .getPurchaseDate()
                                            .getMonth()
                                            .getValue() >= statisticsDetailed.getStart().getMonth().getValue())
                                    .filter(purchase -> purchase.
                                            getPurchaseDate()
                                            .getMonth()
                                            .getValue() <= statisticsDetailed.getEnd().getMonth().getValue())
                                    .filter(purchase -> purchase.getPurchaseDate().getYear() == LocalDate.now().getYear()).count()
                    );
                }
                case DAILY -> {
                    return new ComplexPurchaseResponse(
                            allPurchases
                                    .stream()
                                    .filter(purchase -> purchase
                                            .getPurchaseDate()
                                            .getDayOfMonth() >= statisticsDetailed.getStart().getDayOfMonth())
                                    .filter(purchase -> purchase.
                                            getPurchaseDate()
                                            .getDayOfMonth() <= statisticsDetailed.getEnd().getDayOfMonth())
                                    .filter(purchase -> purchase.getPurchaseDate().getYear() == LocalDate.now().getYear()).count()

                    );      
                }
                case YEARLY -> {
                    return new ComplexPurchaseResponse(
                            allPurchases
                                    .stream()
                                    .filter(purchase -> purchase
                                            .getPurchaseDate()
                                            .getYear() >= statisticsDetailed.getStart().getYear())
                                    .filter(purchase -> purchase.
                                            getPurchaseDate()
                                            .getYear() <= statisticsDetailed.getEnd().getYear()).count()
                    );
                }
                default -> {
                    return null;
                }
            }
        }
    }
