package com.tencheeduard.hotelapp.config;

import com.tencheeduard.hotelapp.geographicDistanceStrategies.GeographicDistanceStrategy;
import com.tencheeduard.hotelapp.geographicDistanceStrategies.HaversineFormulaStrategy;
import com.tencheeduard.hotelapp.geographicDistanceStrategies.VincentyFormulaStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HotelAppConfiguration {

    @Bean
    public GeographicDistanceStrategy getDistanceStrategy()
    {
        return new VincentyFormulaStrategy();
    }

}
