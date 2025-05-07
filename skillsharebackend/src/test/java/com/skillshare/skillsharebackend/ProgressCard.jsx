import { useState } from 'react';

const ProgressCard = ({ progress, onEdit, onDelete }) => {
  const [showDetails, setShowDetails] = useState(false);
  
  const formatDate = (dateString) => {
    const options = { year: 'numeric', month: 'long', day: 'numeric' };
    return new Date(dateString).toLocaleDateString(undefined, options);
  };
  
  const formatTime = (hours) => {
    if (hours === 1) return '1 hour';
    return `${hours} hours`;
  };
  
  // Define status colors
  const getStatusColor = (status) => {
    switch (status) {
      case 'COMPLETED':
        return 'bg-green-100 text-green-800';
      case 'IN_PROGRESS':
        return 'bg-blue-100 text-blue-800';
      case 'ON_HOLD':
        return 'bg-yellow-100 text-yellow-800';
      default:
        return 'bg-gray-100 text-gray-800';
    }
  };
  
  return (
    <div className="bg-white rounded-lg shadow-md overflow-hidden">
      <div className="p-4">
        <div className="flex items-center justify-between">
          <h3 className="text-lg font-semibold text-gray-800">{progress.title}</h3>
          <span className={`text-xs font-medium px-2 py-1 rounded-full ${getStatusColor(progress.status)}`}>
            {progress.status?.replace('_', ' ')}
          </span>
        </div>
        
        <div className="flex items-center justify-between mt-2 text-sm text-gray-600">
          <div>
            <span>{formatDate(progress.progressDate)}</span>
            <span className="mx-2">•</span>
            <span>{formatTime(progress.hoursSpent)}</span>
          </div>
          <div>{progress.progressPercentage}% Complete</div>
        </div>
        
        <div className="w-full bg-gray-200 rounded-full h-2.5 my-3">
          <div 
            className="bg-primary-600 h-2.5 rounded-full" 
            style={{ width: `${progress.progressPercentage}%` }}
          ></div>
        </div>
        
        <button 
          onClick={() => setShowDetails(!showDetails)}
          className="text-sm font-medium text-primary-600 hover:text-primary-800 focus:outline-none flex items-center mt-2"
        >
          {showDetails ? 'Hide Details' : 'Show Details'}
          <svg 
            xmlns="http://www.w3.org/2000/svg" 
            className={`h-4 w-4 ml-1 transition-transform ${showDetails ? 'rotate-180' : ''}`} 
            fill="none" viewBox="0 0 24 24" stroke="currentColor"
          >
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M19 9l-7 7-7-7" />
          </svg>
        </button>
        
        {showDetails && (
          <div className="mt-4 space-y-4">
            {progress.notes && (
              <div>
                <h4 className="text-sm font-medium text-gray-700 mb-1">Notes</h4>
                <p className="text-sm text-gray-600">{progress.notes}</p>
              </div>
            )}
            
            {progress.skillsLearned && progress.skillsLearned.length > 0 && (
              <div>
                <h4 className="text-sm font-medium text-gray-700 mb-2">Skills Learned</h4>
                <div className="flex flex-wrap gap-2">
                  {progress.skillsLearned.map((skill, index) => (
                    <span
                      key={index}
                      className="bg-primary-50 text-primary-700 text-xs px-2 py-1 rounded-full"
                    >
                      {skill}
                    </span>
                  ))}
                </div>
              </div>
            )}
            
            <div className="pt-4 flex justify-end space-x-2 border-t border-gray-100">
              <button
                onClick={() => onEdit(progress)}
                className="px-3 py-1 text-sm text-primary-600 hover:text-primary-800 border border-primary-600 hover:border-primary-800 rounded-md transition-colors"
              >
                Edit
              </button>
              <button
                onClick={() => onDelete(progress.id)}
                className="px-3 py-1 text-sm text-red-600 hover:text-red-800 border border-red-600 hover:border-red-800 rounded-md transition-colors"
              >
                Delete
              </button>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default ProgressCard;
