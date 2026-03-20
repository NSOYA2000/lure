package com.lurefish.service;

import com.lurefish.model.Fish;
import java.util.List;

public interface FishService {
    List<Fish> getAllFish();
    Fish getFishById(Long id);
    Fish createFish(Fish fish);
    Fish updateFish(Long id, Fish fish);
    void deleteFish(Long id);
}