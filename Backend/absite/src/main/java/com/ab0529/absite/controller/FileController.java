package com.ab0529.absite.controller;

import com.ab0529.absite.entity.File;
import com.ab0529.absite.entity.User;
import com.ab0529.absite.model.ApiResponse;
import com.ab0529.absite.repository.UserRepository;
import com.ab0529.absite.service.FileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/file")
public class FileController {
	@Autowired
	FileServiceImpl fileService;
	@Autowired
	UserRepository userRepository;

	@GetMapping("id/{id}")
	@PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
	public ResponseEntity<?> getFileById(Authentication authentication, @PathVariable UUID id) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		User dbuser = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
		// Attempt to find file in database
		Optional<File> file = fileService.findByIdAndMatchOwner(id, dbuser.getId());

		// Could not find file or unauthorized
		if (file.isEmpty())
			return new ApiResponse(HttpStatus.NOT_FOUND, "File not found", null).responseEntity();

		return new ApiResponse(HttpStatus.OK, "File found", file).responseEntity();
	}

	@GetMapping(value = "v/{id}", produces = {
			MediaType.IMAGE_PNG_VALUE,
			MediaType.IMAGE_JPEG_VALUE,
			MediaType.IMAGE_GIF_VALUE,
	})
	public ResponseEntity<Resource> viewFileByName(@PathVariable UUID id) {
		Optional<File> file = fileService.findById(id);
		// Could not find file or unauthorized
		if (file.isEmpty())
			return (ResponseEntity<Resource>) new ApiResponse(HttpStatus.NOT_FOUND, "File not found", null).responseEntity();

		// Make sure content is an image
		if (!file.get().getContentType().toString().startsWith("image/"))
			return (ResponseEntity<Resource>) new ApiResponse(HttpStatus.NOT_ACCEPTABLE, "File is not a image. Non images goto /d", null).responseEntity();

		byte[] f = file.get().getContent();
		InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(f));

		return ResponseEntity.ok().contentLength(f.length).body(resource);
	}
	@GetMapping("d/{id}")
	@PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
	public ResponseEntity<Resource> downloadFileByName(Authentication authentication, @PathVariable UUID id) {
		Optional<File> file = fileService.findById(id);
		// Could not find file
		if (file.isEmpty())
			return (ResponseEntity<Resource>) new ApiResponse(HttpStatus.NOT_FOUND, "File not found", null).responseEntity();

		// Make sure user owns the file
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		if (!file.get().getUser().getUsername().equals(userDetails.getUsername()))
			return (ResponseEntity<Resource>) new ApiResponse(HttpStatus.UNAUTHORIZED, "Unauthorized access", null).responseEntity();

		byte[] f = file.get().getContent();
		InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(f));
		HttpHeaders headers = new HttpHeaders();
		ContentDisposition contentDisp = ContentDisposition.builder("inline").filename(file.get().getName()).build();
		headers.setContentDisposition(contentDisp);

		return ResponseEntity.ok().headers(headers).contentLength(f.length).contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
	}
	@GetMapping("{name}")
	@PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
	public ResponseEntity<?> getFile(Authentication authentication, @PathVariable String name) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		User dbuser = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
		// Attempt to find file in database
		Iterator<File> files = fileService.findByNameAndMatchOwner(name, dbuser.getId()).iterator();

		// Could not find file or unauthorized
		if (!files.hasNext())
			return new ApiResponse(HttpStatus.NOT_FOUND, "File not found", null).responseEntity();

		return new ApiResponse(HttpStatus.OK, "File found", files).responseEntity();
	}

	@GetMapping("all/user/{max}")
	@PreAuthorize("hasRole('ADMIN') OR hasRole('USER')")
	public ResponseEntity<?> getAllUserFilesLimited(Authentication authentication, @PathVariable int max) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		User dbuser = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
		Iterator<Object> files = fileService.findAllByIdLimited(dbuser.getId(), max).iterator();

		if (!files.hasNext())
			return new ApiResponse(HttpStatus.NOT_FOUND, "Files not found", null).responseEntity();

		return new ApiResponse(HttpStatus.OK, "Files found", files).responseEntity();
	}

	@GetMapping("all/{max}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getAllAdminFilesLimited(@PathVariable int max) {
		Iterator<Object> files = fileService.findAllLimited(max).iterator();

		if (!files.hasNext())
			return new ApiResponse(HttpStatus.NOT_FOUND, "Files not found", null).responseEntity();

		return new ApiResponse(HttpStatus.OK, "Files found", files).responseEntity();
	}

	@PostMapping
	@PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
	public ResponseEntity<?> addFile(Authentication authentication, @RequestParam MultipartFile multipartFile) throws IOException {
		File dbFile = new File();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
		UUID id = UUID.randomUUID();

		dbFile.setId(id);
		dbFile.setUser(user);
		dbFile.setName(Objects.requireNonNull(multipartFile.getOriginalFilename()));
		dbFile.setContent(multipartFile.getBytes());
		dbFile.setContentType(Objects.requireNonNull(multipartFile.getContentType()));

		fileService.save(dbFile);

		return new ApiResponse(HttpStatus.OK, "File added", dbFile).responseEntity();
	}

	@DeleteMapping("delete/{id}")
	@PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
	public ResponseEntity<?> deleteFile(Authentication authentication, @PathVariable UUID id, HttpServletRequest request) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		User dbUser = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
		Optional<File> file = fileService.findById(id);

		// Make sure file exists
		if (file.isEmpty())
			return new ApiResponse(HttpStatus.NOT_FOUND, "File not found", null).responseEntity();

		// Make sure user owns the file or is an admin
		if (!request.isUserInRole("ROLE_ADMIN") && file.get().getUser().getId() != dbUser.getId())
			return new ApiResponse(HttpStatus.UNAUTHORIZED, "Unauthorized access", null).responseEntity();

		// Delete the file
		fileService.deleteById(id);

		return new ApiResponse(HttpStatus.OK, "File deleted", null).responseEntity();
	}
}
