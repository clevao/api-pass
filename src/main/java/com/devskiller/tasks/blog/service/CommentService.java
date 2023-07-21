package com.devskiller.tasks.blog.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.devskiller.tasks.blog.exception.PostNotFoundException;
import com.devskiller.tasks.blog.model.Comment;
import com.devskiller.tasks.blog.model.Post;
import com.devskiller.tasks.blog.model.dto.CommentDto;
import com.devskiller.tasks.blog.model.dto.NewCommentDto;
import com.devskiller.tasks.blog.repository.CommentRepository;
import com.devskiller.tasks.blog.repository.PostRepository;

@Service
public class CommentService {

	private PostRepository postRepository;
	private CommentRepository commentRepository;

	public CommentService(PostRepository postRepository, CommentRepository commentRepository) {
		this.postRepository = postRepository;
		this.commentRepository = commentRepository;
	}


	/**
	 * Returns a list of all comments for a blog post with passed id.
	 *
	 * @param postId id of the post
	 * @return list of comments sorted by creation date descending - most recent first
	 */
	public List<CommentDto> getCommentsForPost(Long postId) {
		final List<Comment> comments = commentRepository.getCommentsByPostIdOrderByCreationDateDesc(postId);
		return mapCommentsToCommentDtos(comments);
	}

	private List<CommentDto> mapCommentsToCommentDtos(final List<Comment> comments) {
		return comments.stream()
			.map(this::mapCommentToCommentDto)
			.toList();
	}

	private CommentDto mapCommentToCommentDto(final Comment c) {
		return new CommentDto(
			c.getId(),
			c.getComment(),
			c.getAuthor(),
			c.getCreationDate()
		);
	}

	/**
	 * Creates a new comment
	 *
	 * @param postId id of the post
	 * @param newCommentDto data of new comment
	 * @return id of the created comment
	 *
	 * @throws IllegalArgumentException if postId is null or there is no blog post for passed postId
	 */
	public Long addComment(Long postId, NewCommentDto newCommentDto) {

		if(postId == null) {
			throw new IllegalArgumentException("The parameter postId cannot be null");
		}

		final Optional<Post> post = postRepository.findById(postId);

		if(post.isEmpty()) {
			throw new PostNotFoundException( String.format("There is no post with id %d", postId));
		}

		final Comment comment = mapCommentDtoToComment(newCommentDto, post.get());
		commentRepository.save(comment);

		return comment.getId();
	}

	private Comment mapCommentDtoToComment(final NewCommentDto newCommentDto, final Post post) {
		final Comment comment = new Comment();
		comment.setAuthor(newCommentDto.author());
		comment.setComment(newCommentDto.content());
		comment.setCreationDate(LocalDateTime.now());
		comment.setPostId(post.getId());
		return comment;
	}
}
