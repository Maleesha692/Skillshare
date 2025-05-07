const PostCard = ({ post, onClick }) => {
  return (
    <div 
      onClick={() => onClick(post.id)}
      className="bg-white rounded-lg shadow-md overflow-hidden hover:shadow-lg transition-shadow duration-300 cursor-pointer"
    >
      {post.imageUrl && (
        <div className="h-48 overflow-hidden">
          <img 
            src={post.imageUrl} 
            alt={post.title} 
            className="w-full h-full object-cover"
          />
        </div>
      )}
      <div className="p-4">
        <h2 className="text-xl font-semibold text-primary-700 hover:text-primary-500 mb-2 transition-colors duration-200">
          {post.title}
        </h2>
        <p className="text-gray-600 mb-4 line-clamp-3">{post.content}</p>
        <div className="flex justify-between items-center">
          <div className="flex items-center space-x-2">
            <img 
              src={post.author.avatarUrl || 'https://via.placeholder.com/40'} 
              alt={post.author.name}
              className="w-8 h-8 rounded-full"
            />
            <span className="text-sm text-gray-700">{post.author.name}</span>
          </div>
          <div className="flex items-center space-x-4">
            <span className="text-sm text-gray-500 flex items-center">
              <svg xmlns="http://www.w3.org/2000/svg" className="h-4 w-4 mr-1" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z" />
              </svg>
              {post.likesCount}
            </span>
            <span className="text-sm text-gray-500 flex items-center">
              <svg xmlns="http://www.w3.org/2000/svg" className="h-4 w-4 mr-1" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M7 8h10M7 12h4m1 8l-4-4H5a2 2 0 01-2-2V6a2 2 0 012-2h14a2 2 0 012 2v8a2 2 0 01-2 2h-3l-4 4z" />
              </svg>
              {post.commentsCount}
            </span>
          </div>
        </div>
        
        {/* Display post date */}
        <div className="mt-3 text-xs text-gray-500">
          {new Date(post.createdAt).toLocaleDateString('en-US', {
            year: 'numeric',
            month: 'short',
            day: 'numeric'
          })}
        </div>
      </div>
    </div>
  );
};

export default PostCard;
