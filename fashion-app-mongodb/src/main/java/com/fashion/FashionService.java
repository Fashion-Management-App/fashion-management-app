package com.fashion;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FashionService {

    private final FashionRepository fashionRepository;

    public FashionService(FashionRepository fashionRepository) {
        this.fashionRepository = fashionRepository;
    }

    public List<Fashion> viewAllFittings() {
        return fashionRepository.findAll();
    }

    public void deleteAllFittings() {
        fashionRepository.deleteAll();
    }

    public void deleteFittingById(String id) {
        fashionRepository.deleteById(id);
    }

    public Fashion saveFitting(Fashion fashion) {
        return fashionRepository.save(fashion);
    }

    public List<Fashion> importFashionFromCsv() {
        List<Fashion> fashions = new CsvImporter().importFromCsv();
        return fashionRepository.saveAll(fashions);
    }

    public Fashion getFittingById(String id) {
        return fashionRepository.findById(id).orElse(null);
    }

}
