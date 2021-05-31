package com.waracle.cakemgr.controller;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.waracle.cakemgr.model.CakeDTO;
import com.waracle.cakemgr.service.CakeService;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping(value = "/cakes")
public class CakeController {

	@GetMapping(value = "", produces = "application/json")
	public List<CakeDTO> fetchAll() throws EntityNotFoundException {
		return CakeService.fetchAll();
	}

	/*
	@GetMapping(value = {"/", ""}, params = {
			"page", "size"
	})
	public Page<CakeDTO> fetchAllPaged(@RequestParam("page") int page, @RequestParam("size") int size) throws EntityNotFoundException {

		Page<CakeDTO> answer = CakeService.fetchAll(PageRequest.of(page, size));

		return answer;

	}

	@GetMapping("/{id}")
	public List<CakeResource> fetchById(@PathVariable(required = true) Long id) throws EntityNotFoundException {
		List<CakeResource> result =
				CakeService.fetchById(id).stream()
				.map(r -> createResourceFor(r))
				.collect(Collectors.toList());
		
		return result;
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable(required = true) Long id) throws EntityNotFoundException {
		CakeService.deleteReadingById(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@DeleteMapping(value = {"/", ""})
	public ResponseEntity<Void> deleteAll(HttpServletRequest req) throws EntityNotFoundException {
		log.info("IP Addr: " + fetchClientIpAddr());
		if (fetchClientIpAddr().equals("127.0.0.1")) {
			CakeService.deleteAll();
			return new ResponseEntity<Void>(HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(value = {"/", ""}, produces = MediaType.APPLICATION_JSON_VALUE) //
	public ResponseEntity<CakeResource> addNewReading(@RequestBody CakeDTO newReading) {
		
		if (newReading.getTimestampMsecs() == null) {
			newReading.setTimestampMsecs(Instant.now().toEpochMilli());
		}
		
		CakeDTO updatedReading = CakeService.createOrUpdateReading(newReading);
		CakeResource toReturn = createResourceFor(updatedReading);
		
		return new ResponseEntity<>(toReturn, HttpStatus.CREATED);
		
	}

	private CakeResource createResourceFor(CakeDTO updatedReading) {
		CakeResource toReturn = CakeResource.builder().content(updatedReading).build();
		Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CakeController.class).fetchById(updatedReading.getId())).withSelfRel();
		toReturn.add(selfLink);
		return toReturn;
	}

	@RequestMapping(value = "/imageForReading", method = RequestMethod.POST, consumes = {
			"multipart/form-data"
	})
	ResponseEntity<Void> addImageForReading(@RequestParam("readingId") Long readingId, @RequestParam("imageFile") MultipartFile part) throws Exception {

		byte[] incomingBytes = part.getBytes();
		System.out.println("ID=" + readingId + "\n" + Arrays.toString(incomingBytes));

		InputStream is = new ByteArrayInputStream(incomingBytes);
		BufferedImage bufferedImage = ImageIO.read(is);

		File outputfile = new File("" + readingId + ".jpg");
		ImageIO.write(bufferedImage, "jpg", outputfile);

		URI fullPath = outputfile.toURI();

		CakeService.setImageFilename(readingId, fullPath);

		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);

	}
	*/

	@Autowired
	private CakeService CakeService;

	@SuppressWarnings("ConstantConditions")
	protected String fetchClientIpAddr() {
		HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest();
		String ip = Optional.ofNullable(request.getHeader("X-FORWARDED-FOR")).orElse(request.getRemoteAddr());
		if (ip.equals("0:0:0:0:0:0:0:1")) {
			ip = "127.0.0.1";
		}
		return ip;
	}
}
