package ru.netology.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.netology.model.Post;
import ru.netology.service.PostService;

import java.util.List;

@RestController
@RequestMapping("api/posts")
public class PostController {
	private final PostService service;

	public PostController(PostService service) {
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<List<Post>> all() {
		final List<Post> unDeletedPosts = service.all();
		return unDeletedPosts != null && !unDeletedPosts.isEmpty()
						? new ResponseEntity<>(unDeletedPosts, HttpStatus.OK)
						: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("{/id")
	public ResponseEntity<Post> getById(@PathVariable long id) {
		Post currentPost = service.getById(id);
		return currentPost != null
						? new ResponseEntity<>(currentPost, HttpStatus.OK)
						: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping
	public ResponseEntity<Post> save(@RequestBody Post post) {
		if (post.isRemoved()) {
			return ResponseEntity.notFound().build();
		}
		return new ResponseEntity<>(post, HttpStatus.OK);
	}

	@DeleteMapping("/{id")
	public void removeById(long id) {
		service.removeById(id);
	}
}