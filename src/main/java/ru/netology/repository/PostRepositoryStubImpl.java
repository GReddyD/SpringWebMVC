package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class PostRepositoryStubImpl implements PostRepository {
	final private List<Post> listPost = new CopyOnWriteArrayList<>();
	final private AtomicInteger ID = new AtomicInteger(0);


	public List<Post> all() {
		for (Post post : listPost) {
			if (!post.isRemoved()) {
				System.out.println("ID: " + post.getId() + ", CurrentContent:" + post.getContent());
			}
		}
		return listPost;
	}

	public Optional<Post> getById(long id) {
		Post postCurrent = null;
		for (Post post : listPost) {
			if (post.getId() == id) {
				if (post.isRemoved()) {
					throw new NotFoundException("POST удалён");
				} else {
					postCurrent = post;
				}
			} else {
				System.out.println("ID отсутствует");
			}
		}
		return Optional.ofNullable(postCurrent);
	}

	public Post save(Post post) {
		if (post.getId() == 0) {
			ID.getAndIncrement();
			post.setId(ID.get());

		} else {
			for (Post currentPost : listPost) {
				if (currentPost.getId() == post.getId()) {
					if (post.isRemoved()) {
						throw new NotFoundException();
					} else {
						currentPost.setContent(post.getContent());
					}
				} else {
					ID.getAndIncrement();
					System.out.println("Новый ID: " + ID.get());
					post.setId(ID.get());
				}
			}
		}
		post.setRemoved(false);
		listPost.add(post);
		return post;
	}

	public void removeById(long id) {
		for (Post post : listPost) {
			if (post.getId() == id) {
				post.setRemoved(true);
			} else {
				System.out.println("ID отсутствует");
			}
		}
	}
}