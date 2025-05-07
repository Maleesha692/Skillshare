import { useState, useEffect } from 'react';

const PostDetail = ({ postId, onBack }) => {
  const [post, setPost] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [comment, setComment] = useState('');
  
  useEffect(() => {
    // Simulate fetching post details
    const fetchPostDetails = async () => {
      try {
        // In a real app, you would use an API call like:
        // const data = await postService.getPostById(postId);
        
        // Mock data for demonstration
        setTimeout(() => {
          const mockPost = {
            id: postId,
            title: "Understanding Synchronous API Calls",
            content: `
              Synchronous API calls wait for the operation to complete before continuing execution.
              
              In JavaScript, we use async/await syntax to handle asynchronous operations in a synchronous manner:
              
              \`\`\`javascript
              async function fetchData() {
                try {
                  const response = await fetch('/api/data');
                  const data = await response.json();
                  return data;
                } catch (error) {
                  console.error('Error fetching data:', error);
                  throw error;
                }
              }
              \`\`\`
              
              This approach makes code more readable and maintainable compared to using callbacks or promise chains.
            `,
            author: {
              id: 123,
              name: "Technical Writer",
              avatarUrl: "https://via.placeholder.com/60"
            },
            createdAt: "2025-04-26T14:30:00Z",
            likesCount: 42,
            liked: false,
            comments: [
              {
                id: 1,
                content: "Great explanation! This helped me understand the concept.",
                author: {
                  name: "Beginner Coder",
                  avatarUrl: "https://via.placeholder.com/40"
                },
                createdAt: "2025-04-26T15:45:00Z"
              },
              {
                id: 2,
                content: "I'd also recommend looking into Promise.all for handling multiple async operations simultaneously.",
                author: {
                  name: "Senior Dev",
                  avatarUrl: "https://via.placeholder.com/40"
                },
                createdAt: "2025-04-26T16:20:00Z"
              }
            ]
          };
          
          setPost(mockPost);
          setLoading(false);
        }, 800);
        
      } catch (err) {
        setError('Failed to fetch post details. Please try again later.');
        setLoading(false);
      }
    };
    
    fetchPostDetails();
  }, [postId]);
  
  const handleLike = () => {
    setPost(prev => ({
      ...prev,
      likesCount: prev.liked ? prev.likesCount - 1 : prev.likesCount + 1,
      liked: !prev.liked
    }));
  };
  
  const handleCommentSubmit = (e) => {
    e.preventDefault();
    
    if (!comment.trim()) return;
    
    const newComment = {
      id: Date.now(),
      content: comment,
      author: {
        name: "You",
        avatarUrl: "https://via.placeholder.com/40"
      },
      createdAt: new Date().toISOString()
    };
    
    setPost(prev => ({
      ...prev,
      comments: [newComment, ...prev.comments]
    }));
    
    setComment('');
  };
  
  if (loading) {
    return (
      <div className="flex justify-center items-center h-64">
        <div className="animate-spin rounded-full h-10 w-10 border-t-2 border-b-2 border-blue-500"></div>
      </div>
    );
  }
  
  if (error || !post) {
    return (
      <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative" role="alert">
        <strong className="font-bold">Error!</strong>
        <span className="block sm:inline"> {error || "Post not found"}</span>
      </div>
    );
  }
  
  return (
    <div className="bg-white rounded-lg shadow-md overflow-hidden">
      <div className="p-6">
        {/* Back button */}
        <button 
          onClick={onBack}
          className="flex items-center text-blue-600 hover:text-blue-800 mb-4 transition-colors duration-200"
        >
          <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5 mr-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M10 19l-7-7m0 0l7-7m-7 7h18" />
          </svg>
          Back to posts
        </button>
        
        {/* Post header */}
        <h1 className="text-2xl font-bold text-gray-800 mb-4">{post.title}</h1>
        
        {/* Author info */}
        <div className="flex items-center mb-6">
          <img 
            src={post.author.avatarUrl} 
            alt={post.author.name}
            className="w-10 h-10 rounded-full mr-3"
          />
          <div>
            <div className="text-gray-800 font-medium">{post.author.name}</div>
            <div className="text-gray-500 text-sm">
              {new Date(post.createdAt).toLocaleDateString('en-US', { 
                year: 'numeric', 
                month: 'long', 
                day: 'numeric' 
              })}
            </div>
          </div>
        </div>
        
        {/* Post content */}
        <div className="prose max-w-none mb-8 text-gray-700 whitespace-pre-line">
          {post.content}
        </div>
        
        {/* Actions */}
        <div className="flex items-center space-x-4 mb-8">
          <button 
            onClick={handleLike}
            className={`flex items-center space-x-1 ${post.liked ? 'text-red-500' : 'text-gray-500'} hover:text-red-500 transition-colors duration-200`}
          >
            <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6" fill={post.liked ? "currentColor" : "none"} viewBox="0 0 24 24" stroke="currentColor">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={post.liked ? 0 : 2} d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z" />
            </svg>
            <span>{post.likesCount} likes</span>
          </button>
          
          <div className="flex items-center space-x-1 text-gray-500">
            <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M7 8h10M7 12h4m1 8l-4-4H5a2 2 0 01-2-2V6a2 2 0 012-2h14a2 2 0 012 2v8a2 2 0 01-2 2h-3l-4 4z" />
            </svg>
            <span>{post.comments.length} comments</span>
          </div>
        </div>
        
        {/* Add comment form */}
        <div className="mb-8">
          <h3 className="text-lg font-semibold text-gray-800 mb-3">Add a comment</h3>
          <form onSubmit={handleCommentSubmit}>
            <textarea
              value={comment}
              onChange={(e) => setComment(e.target.value)}
              className="w-full border border-gray-300 rounded-lg p-3 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-all duration-200"
              rows="3"
              placeholder="Write your comment here..."
            ></textarea>
            <div className="flex justify-end mt-2">
              <button 
                type="submit"
                className="bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded-md transition-colors duration-200"
                disabled={!comment.trim()}
              >
                Submit
              </button>
            </div>
          </form>
        </div>
        
        {/* Comments */}
        <div>
          <h3 className="text-lg font-semibold text-gray-800 mb-4">Comments</h3>
          
          {post.comments.length === 0 ? (
            <div className="text-gray-500">No comments yet. Be the first to comment!</div>
          ) : (
            <div className="space-y-4">
              {post.comments.map(comment => (
                <div key={comment.id} className="border-b border-gray-100 pb-4">
                  <div className="flex items-center mb-2">
                    <img 
                      src={comment.author.avatarUrl} 
                      alt={comment.author.name}
                      className="w-8 h-8 rounded-full mr-2"
                    />
                    <div>
                      <div className="text-gray-800 font-medium">{comment.author.name}</div>
                      <div className="text-gray-400 text-xs">
                        {new Date(comment.createdAt).toLocaleDateString('en-US', { 
                          year: 'numeric', 
                          month: 'short', 
                          day: 'numeric' 
                        })}
                      </div>
                    </div>
                  </div>
                  <div className="text-gray-700 pl-10">{comment.content}</div>
                </div>
              ))}
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default PostDetail;
