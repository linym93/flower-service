package com.linym.flowerservice.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

public class UserDocApiError {

		   private HttpStatus status;
		   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy hh:mm:ss")
		   private LocalDateTime timestamp;
		   private String message;
		   private String debugMessage;
		   
		   
	   public HttpStatus getStatus() {
			return status;
		}

		public void setStatus(HttpStatus status) {
			this.status = status;
		}

		public LocalDateTime getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(LocalDateTime timestamp) {
			this.timestamp = timestamp;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public String getDebugMessage() {
			return debugMessage;
		}

		public void setDebugMessage(String debugMessage) {
			this.debugMessage = debugMessage;
		}

		   
		   private UserDocApiError() {
		       timestamp = LocalDateTime.now();
		   }

		   UserDocApiError(HttpStatus status) {
		       this();
		       this.status = status;
		   }

		   UserDocApiError(HttpStatus status, Throwable ex) {
		       this();
		       this.status = status;
		       this.message = "Unexpected error";
		       this.debugMessage = ex.getLocalizedMessage();
		   }

		   UserDocApiError(HttpStatus status, String message, Throwable ex) {
		       this();
		       this.status = status;
		       this.message = message;
		       this.debugMessage = ex.getLocalizedMessage();
		   }

}
