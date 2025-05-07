package com.example.demo.common.file.dto;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name="imageFileName")
@Table(name="imageFileName")
@Component
@Setter @Getter
@ToString
public class FileDTO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int imageNo;
	
	private int articleNo;
	private String fileName; 
}
