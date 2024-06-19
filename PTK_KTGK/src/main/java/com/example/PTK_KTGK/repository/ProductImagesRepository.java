package com.example.PTK_KTGK.repository;


import com.example.PTK_KTGK.Model.ProductImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImagesRepository extends JpaRepository<ProductImages, Long> {
}
