import { useState } from 'react';

const ProgressEntryForm = ({ onSubmit, initialData, planId }) => {
  const [formData, setFormData] = useState(initialData || {
    title: '',
    notes: '',
    progressDate: new Date().toISOString().split('T')[0],
    hoursSpent: 0.5,
    skillsLearned: [],
    progressPercentage: 0,
    status: 'IN_PROGRESS'
  });

  const [currentSkill, setCurrentSkill] = useState('');
  const [errors, setErrors] = useState({});
  const [submitting, setSubmitting] = useState(false);

  const handleChange = (e) => {
    const { name, value, type } = e.target;
    setFormData({
      ...formData,
      [name]: type === 'number' ? parseFloat(value) : value
    });

    // Clear error for this field if it exists
    if (errors[name]) {
      setErrors({
        ...errors,
        [name]: ''
      });
    }
  };

  const handleSkillAdd = (e) => {
    e.preventDefault();
    if (!currentSkill.trim()) return;

    setFormData({
      ...formData,
      skillsLearned: [...formData.skillsLearned, currentSkill.trim()]
    });

    setCurrentSkill('');
  };

  const handleSkillRemove = (index) => {
    const updatedSkills = [...formData.skillsLearned];
    updatedSkills.splice(index, 1);
    setFormData({
      ...formData,
      skillsLearned: updatedSkills
    });
  };

  const validateForm = () => {
    const newErrors = {};

    if (!formData.title.trim()) {
      newErrors.title = 'Title is required';
    }

    if (!formData.progressDate) {
      newErrors.progressDate = 'Progress date is required';
    }

    if (formData.hoursSpent <= 0) {
      newErrors.hoursSpent = 'Hours spent must be greater than 0';
    }

    if (formData.progressPercentage < 0 || formData.progressPercentage > 100) {
      newErrors.progressPercentage = 'Progress percentage must be between 0 and 100';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!validateForm()) return;

    setSubmitting(true);

    try {
      await onSubmit(formData);
      setFormData({
        title: '',
        notes: '',
        progressDate: new Date().toISOString().split('T')[0],
        hoursSpent: 0.5,
        skillsLearned: [],
        progressPercentage: 0,
        status: 'IN_PROGRESS'
      });
    } catch (error) {
      console.error('Error submitting progress:', error);
      setErrors({
        submit: 'Failed to submit progress. Please try again.'
      });
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-4">
      <div>
        <label htmlFor="title" className="block text-sm font-medium text-gray-700 mb-1">
          Progress Title*
        </label>
        <input
          type="text"
          id="title"
          name="title"
          value={formData.title}
          onChange={handleChange}
          className={`w-full px-3 py-2 border rounded-md ${errors.title ? 'border-red-500' : 'border-gray-300'}`}
          placeholder="e.g., Completed React Components Module"
        />
        {errors.title && (
          <p className="mt-1 text-sm text-red-600">{errors.title}</p>
        )}
      </div>

      <div>
        <label htmlFor="notes" className="block text-sm font-medium text-gray-700 mb-1">
          Notes
        </label>
        <textarea
          id="notes"
          name="notes"
          value={formData.notes}
          onChange={handleChange}
          rows="3"
          className="w-full px-3 py-2 border border-gray-300 rounded-md"
          placeholder="What did you learn or accomplish? Any challenges?"
        ></textarea>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div>
          <label htmlFor="progressDate" className="block text-sm font-medium text-gray-700 mb-1">
            Date*
          </label>
          <input
            type="date"
            id="progressDate"
            name="progressDate"
            value={formData.progressDate}
            onChange={handleChange}
            className={`w-full px-3 py-2 border rounded-md ${errors.progressDate ? 'border-red-500' : 'border-gray-300'}`}
          />
          {errors.progressDate && (
            <p className="mt-1 text-sm text-red-600">{errors.progressDate}</p>
          )}
        </div>

        <div>
          <label htmlFor="hoursSpent" className="block text-sm font-medium text-gray-700 mb-1">
            Hours Spent*
          </label>
          <input
            type="number"
            id="hoursSpent"
            name="hoursSpent"
            value={formData.hoursSpent}
            onChange={handleChange}
            min="0.5"
            step="0.5"
            className={`w-full px-3 py-2 border rounded-md ${errors.hoursSpent ? 'border-red-500' : 'border-gray-300'}`}
          />
          {errors.hoursSpent && (
            <p className="mt-1 text-sm text-red-600">{errors.hoursSpent}</p>
          )}
        </div>
      </div>

      <div>
        <label htmlFor="progressPercentage" className="block text-sm font-medium text-gray-700 mb-1">
          Overall Progress ({formData.progressPercentage}%)
        </label>
        <input
          type="range"
          id="progressPercentage"
          name="progressPercentage"
          value={formData.progressPercentage}
          onChange={handleChange}
          min="0"
          max="100"
          step="5"
          className="w-full h-2 bg-gray-200 rounded-lg appearance-none cursor-pointer"
        />
        {errors.progressPercentage && (
          <p className="mt-1 text-sm text-red-600">{errors.progressPercentage}</p>
        )}
      </div>

      <div>
        <label className="block text-sm font-medium text-gray-700 mb-1">
          Skills Learned
        </label>
        <div className="flex">
          <input
            type="text"
            value={currentSkill}
            onChange={(e) => setCurrentSkill(e.target.value)}
            className="flex-grow px-3 py-2 border border-gray-300 rounded-l-md"
            placeholder="Add a skill you learned"
            onKeyPress={(e) => {
              if (e.key === 'Enter') {
                handleSkillAdd(e);
              }
            }}
          />
          <button
            type="button"
            onClick={handleSkillAdd}
            className="px-4 py-2 bg-primary-600 text-white rounded-r-md hover:bg-primary-700"
          >
            Add
          </button>
        </div>

        {formData.skillsLearned.length > 0 && (
          <div className="mt-2 flex flex-wrap gap-2">
            {formData.skillsLearned.map((skill, index) => (
              <span
                key={index}
                className="bg-primary-100 text-primary-800 text-sm px-3 py-1 rounded-full flex items-center"
              >
                {skill}
                <button
                  type="button"
                  onClick={() => handleSkillRemove(index)}
                  className="ml-2 text-primary-600 hover:text-primary-800"
                >
                  <svg xmlns="http://www.w3.org/2000/svg" className="h-4 w-4" viewBox="0 0 20 20" fill="currentColor">
                    <path fillRule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clipRule="evenodd" />
                  </svg>
                </button>
              </span>
            ))}
          </div>
        )}
      </div>

      {errors.submit && (
        <div className="mt-4 bg-red-100 border-l-4 border-red-500 text-red-700 p-4">
          <p>{errors.submit}</p>
        </div>
      )}

      <div className="flex justify-end">
        <button
          type="submit"
          disabled={submitting}
          className={`px-4 py-2 bg-primary-600 text-white rounded-md hover:bg-primary-700 ${submitting ? 'opacity-70 cursor-not-allowed' : ''}`}
        >
          {submitting ? 'Submitting...' : 'Record Progress'}
        </button>
      </div>
    </form>
  );
};

export default ProgressEntryForm;
