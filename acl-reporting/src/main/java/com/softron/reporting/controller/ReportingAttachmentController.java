package com.softron.reporting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softron.core.annotations.ApiController;
import com.softron.reporting.service.ReportingAttachmentService;
import com.softron.reporting.to.AttachmentTO;
import com.softron.reporting.to.ReplyAttachmentTO;

import static com.softron.core.constants.ApiConstants.CHILD_ID;
import static com.softron.core.constants.ApiConstants.EXTERNAL_MEDIA_TYPE;
import static com.softron.core.constants.ApiConstants.ID;
import static com.softron.core.constants.ReportingApiContants.OR_ATTACH_ENDPOINT;
import static com.softron.core.constants.ReportingApiContants.OR_ATTCH_DOWNLOAD_ENDPOINT;
import static com.softron.core.constants.ReportingApiContants.OR_REPLY_ATTACH_ENDPOINT;
import static com.softron.core.constants.ReportingApiContants.OR_SENDER_ATTACH_ENDPOINT;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@ApiController
public class ReportingAttachmentController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReportingAttachmentController.class);

	@Autowired
	private ReportingAttachmentService service;

	@Autowired
	private ObjectMapper objectMapper;

	@GetMapping(value = OR_ATTACH_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
	public ResponseEntity<List<AttachmentTO>> getUserOrgAttachments(@PathVariable(ID) String reportId) {
		return ResponseEntity.ok(service.getOrgAttachments(reportId));
	}

	@GetMapping(value = OR_SENDER_ATTACH_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
	public ResponseEntity<List<AttachmentTO>> getSenderAttachments(@PathVariable(ID) String reportId) {
		return ResponseEntity.ok(service.getSenderAttachments(reportId));
	}

	@GetMapping(value = OR_REPLY_ATTACH_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
	public ResponseEntity<List<ReplyAttachmentTO>> getReplyAttachments(@PathVariable(ID) String reportId) {
		return ResponseEntity.ok(service.getReplyAttachments(reportId));
	}

	@PostMapping(value = OR_ATTACH_ENDPOINT, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE,
			MediaType.APPLICATION_OCTET_STREAM_VALUE }, produces = EXTERNAL_MEDIA_TYPE)
	public ResponseEntity<Void> save(@PathVariable(ID) String reportId, @RequestParam("json") String data,
			@RequestParam("file") final MultipartFile file) throws IOException {
		final AttachmentTO dataTO = objectMapper.readValue(data, AttachmentTO.class);
		service.save(reportId, dataTO, file);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@DeleteMapping(value = OR_ATTACH_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
	public ResponseEntity<Void> delete(@PathVariable(ID) Long id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping(value = OR_ATTCH_DOWNLOAD_ENDPOINT, produces = EXTERNAL_MEDIA_TYPE)
	public ResponseEntity<Resource> download(@PathVariable(ID) String id, @PathVariable(CHILD_ID) Long childId,
			@RequestParam(name = "multiple", required = false) Boolean multiple, HttpServletRequest request)
			throws IOException {

		Resource resource = null;
		if (multiple != null && multiple) {
			resource = service.downloadFiles(id, childId);
		} else {
			resource = service.download(childId);
		}
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			LOGGER.info("Could not determine file type.");
		}

		// Fallback to the default content type if type could not be determined
		if (contentType == null) {
			contentType = "application/octet-stream";
		}

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
}
