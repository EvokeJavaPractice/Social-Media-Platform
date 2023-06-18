package spring.angular.social.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.angular.social.entity.Post;
import spring.angular.social.entity.User;
import spring.angular.social.repository.PostRepository;

@Service
public class PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public List<Post> getUserPosts(User user) {
        return postRepository.findByUser(user);
    }

	public Post save(Post post) {
		return postRepository.save(post);
	}

	public void delete(Long id) {
		 postRepository.deleteById(id);
	}

	public Post update(Long postId, Post post) {
		Optional<Post> optionalPost = postRepository.findById(postId);
		return optionalPost.map(p -> {
			p.setContent(post.getContent());
			return postRepository.save(p);
		}).orElse(null);
	}


}