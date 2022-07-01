package com.ab0529.absiteold.service;

import com.ab0529.absiteold.entity.File;
import com.ab0529.absiteold.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class FileServiceImpl {
	@Autowired
	FileRepository fileRepository;

	@Transactional
	public Iterable<File> findByNameAndMatchOwner(String name, Long id) {
		return fileRepository.findByNameAndUserId(name, id);
	}

	@Transactional
	public Iterable<Object> findAllLimited(Integer max) {
		return fileRepository.findAllLimited(max);
	}
	public Optional<File> findByIdAndMatchOwner(UUID fileId, Long userId) {
		return fileRepository.findByIdAndUserId(fileId, userId);
	}

	public File save(File dbFile) {
		return fileRepository.save(dbFile);
	}

	public Optional<File> findById(UUID id) {
		return fileRepository.findById(id);
	}

	public Iterable<Object> findAllByIdLimited(long id, int max) {
		return fileRepository.findAllByIdLimited(id, max);
	}

	public void deleteById(UUID id) {
		fileRepository.deleteById(id);
	}
}
