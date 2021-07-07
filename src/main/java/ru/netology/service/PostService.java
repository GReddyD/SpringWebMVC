package ru.netology.service;

import org.springframework.stereotype.Service;
import ru.netology.model.Post;
import ru.netology.repository.PostRepositoryStubImpl;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class PostService {

	private final PostRepositoryStubImpl repository;
	private final List<Post> unDeletedPosts = new CopyOnWriteArrayList<>();

	public PostService(PostRepositoryStubImpl repositoryStub) {
		this.repository = repositoryStub;
	}

	public List<Post> all() {
		for (Post post : repository.all()
		) {
			if (!post.isRemoved()) {
				unDeletedPosts.add(post);
			}
		}
		return unDeletedPosts;
	}

	public Post getById(long id) {
		Post postCurrent = null;
		for (Post post : repository.all()) {
			if (post.getId() == id) {
				if (!post.isRemoved()) {
					postCurrent = post;
				}
			} else {
				System.out.println("ID отсутствует");
			}
		}
		return postCurrent;
	}

	public Post save(Post post) {
		return repository.save(post);
	}

	public void removeById(long id) {
		repository.removeById(id);
	}
}