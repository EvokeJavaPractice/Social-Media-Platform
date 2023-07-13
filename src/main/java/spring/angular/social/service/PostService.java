package spring.angular.social.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import spring.angular.social.dto.PostDto;
import spring.angular.social.entity.Post;
import spring.angular.social.entity.User;
import spring.angular.social.repository.PostRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

	private final PostRepository postRepository;

	@Autowired
	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

//    public List<Post> getAllPosts() {
//        return postRepository.findAll();
//    }

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

	public PostDto createPost(PostDto postDto) {

		// convert DTO to entity
		Post post = mapToEntity(postDto);
		Post newPost = postRepository.save(post);

		// convert entity to DTO
		PostDto postDto1 = mapToDTO(newPost);
		return postDto1;
	}

	public List<PostDto> getAllPosts(int pageNo, int pageRecords) { //, String sortBy, String sortDir

		// create Pageable instance
		Pageable pageable = PageRequest.of(pageNo, pageRecords);// sort

		Page<Post> posts = postRepository.findAll(pageable);

		// get content for page object
		List<Post> listOfPosts = posts.getContent();

		List<PostDto> content = listOfPosts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());

		return content;
	}


	private PostDto mapToDTO(Post post) {
		PostDto postDto = new PostDto();
		postDto.setId(post.getId());
		postDto.setContent(post.getContent());
		postDto.setUser(post.getUser());
		return postDto;
	}

	
	private Post mapToEntity(PostDto postDto) {
		Post post = new Post();
		post.setContent(postDto.getContent());
		post.setUser(postDto.getUser());
		return post;
	}
}
