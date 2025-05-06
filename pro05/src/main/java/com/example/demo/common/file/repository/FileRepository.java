package com.example.demo.common.file.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.common.file.dto.FileDTO;

public interface FileRepository extends JpaRepository<FileDTO, Integer> {
	List<FileDTO> findByArticleNo(int articleNo);
}

