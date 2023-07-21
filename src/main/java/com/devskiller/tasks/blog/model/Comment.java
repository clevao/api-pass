package com.devskiller.tasks.blog.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Comment {

	@Id
	@GeneratedValue
	private Long id;

	private Long postId;

	@Column(length = 4096)
	private String author;
	@Column(length = 4096)
	private String comment;

	private LocalDateTime creationDate;

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String title) {
		this.author = title;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public Long getId() {
		return id;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

}
