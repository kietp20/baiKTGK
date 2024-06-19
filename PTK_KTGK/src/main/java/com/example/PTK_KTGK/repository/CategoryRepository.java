package com.example.PTK_KTGK.repository;


import com.example.PTK_KTGK.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository

public interface CategoryRepository extends JpaRepository<Category, Long>{
}
