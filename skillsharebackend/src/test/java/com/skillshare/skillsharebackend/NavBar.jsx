import { useState } from 'react';
import SearchBar from './SearchBar';

const NavBar = ({ user, onLogin, onLogout, onNavigate, currentView }) => {
  const [isMenuOpen, setIsMenuOpen] = useState(false);

  const toggleMenu = () => {
    setIsMenuOpen(!isMenuOpen);
  };

  return (
    <nav className="bg-primary-600 shadow-md">
      <div className="container mx-auto px-4">
        <div className="flex justify-between h-16">          <div className="flex">
            <div className="flex-shrink-0 flex items-center">
              <span className="text-white text-2xl font-bold">Learning Platform</span>
            </div>            <div className="hidden md:ml-6 md:flex md:space-x-4 md:items-center">
              <button 
                onClick={() => onNavigate('home')}
                className={`text-white hover:bg-primary-500 px-3 py-2 rounded-md font-medium ${currentView === 'home' ? 'bg-primary-700' : ''}`}
              >
                Home
              </button>
              <button 
                onClick={() => onNavigate('plans')}
                className={`text-white hover:bg-primary-500 px-3 py-2 rounded-md font-medium ${currentView === 'plans' || currentView === 'plan-detail' ? 'bg-primary-700' : ''}`}
              >
                Learning Plans
              </button>
              <a href="#" className="text-white hover:bg-primary-500 px-3 py-2 rounded-md font-medium">Courses</a>
              <a href="#" className="text-white hover:bg-primary-500 px-3 py-2 rounded-md font-medium">Community</a>
            </div>
          </div>
          
          <div className="hidden md:flex md:items-center md:ml-4">
            <SearchBar onSearch={(query) => console.log('Search for:', query)} />
          </div>
          
          <div className="hidden md:flex md:items-center">
            {user ? (
              <div className="flex items-center space-x-4">
                <div className="relative group">
                  <button className="flex items-center space-x-2 text-white focus:outline-none">
                    <img 
                      src={user.avatarUrl || 'https://via.placeholder.com/40'} 
                      alt={user.name} 
                      className="h-8 w-8 rounded-full"
                    />
                    <span>{user.name}</span>
                    <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" viewBox="0 0 20 20" fill="currentColor">
                      <path fillRule="evenodd" d="M5.293 7.293a1 1 0 011.414 0L10 10.586l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z" clipRule="evenodd" />
                    </svg>
                  </button>
                  
                  <div className="absolute right-0 mt-2 w-48 bg-white rounded-md shadow-lg py-1 opacity-0 invisible group-hover:opacity-100 group-hover:visible transition-all duration-200 ease-in-out">
                    <a href="#" className="block px-4 py-2 text-gray-700 hover:bg-gray-100">Your Profile</a>
                    <a href="#" className="block px-4 py-2 text-gray-700 hover:bg-gray-100">Settings</a>
                    <button
                      onClick={onLogout}
                      className="block w-full text-left px-4 py-2 text-gray-700 hover:bg-gray-100"
                    >
                      Sign out
                    </button>
                  </div>
                </div>
              </div>
            ) : (
              <button
                onClick={onLogin}
                className="bg-white text-primary-600 px-4 py-2 rounded-md font-medium hover:bg-gray-100 transition-colors duration-200"
              >
                Login
              </button>
            )}
          </div>
          
          <div className="flex items-center md:hidden">
            <button
              onClick={toggleMenu}
              className="text-white hover:text-gray-300 focus:outline-none"
            >
              <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M4 6h16M4 12h16M4 18h16" />
              </svg>
            </button>
          </div>
        </div>
      </div>
        {/* Mobile menu */}
      <div className={`${isMenuOpen ? 'block' : 'hidden'} md:hidden`}>
        <div className="px-2 pt-2 pb-3 space-y-1">
          <button 
            onClick={() => {
              onNavigate('home');
              setIsMenuOpen(false);
            }}
            className={`block w-full text-left text-white hover:bg-primary-500 px-3 py-2 rounded-md font-medium ${currentView === 'home' ? 'bg-primary-700' : ''}`}
          >
            Home
          </button>
          <button 
            onClick={() => {
              onNavigate('plans');
              setIsMenuOpen(false);
            }}
            className={`block w-full text-left text-white hover:bg-primary-500 px-3 py-2 rounded-md font-medium ${currentView === 'plans' || currentView === 'plan-detail' ? 'bg-primary-700' : ''}`}
          >
            Learning Plans
          </button>
          <a href="#" className="block text-white hover:bg-primary-500 px-3 py-2 rounded-md font-medium">Courses</a>
          <a href="#" className="block text-white hover:bg-primary-500 px-3 py-2 rounded-md font-medium">Community</a>
        </div>
        
        {user ? (
          <div className="border-t border-primary-700 pt-4 pb-3">
            <div className="flex items-center px-4">
              <div className="flex-shrink-0">
                <img 
                  src={user.avatarUrl || 'https://via.placeholder.com/40'} 
                  alt={user.name} 
                  className="h-10 w-10 rounded-full"
                />
              </div>
              <div className="ml-3">
                <div className="text-base font-medium text-white">{user.name}</div>
                <div className="text-sm font-medium text-primary-300">{user.email}</div>
              </div>
            </div>
            <div className="mt-3 px-2 space-y-1">
              <a href="#" className="block text-white hover:bg-primary-500 px-3 py-2 rounded-md font-medium">Your Profile</a>
              <a href="#" className="block text-white hover:bg-primary-500 px-3 py-2 rounded-md font-medium">Settings</a>
              <button
                onClick={onLogout}
                className="w-full text-left block text-white hover:bg-primary-500 px-3 py-2 rounded-md font-medium"
              >
                Sign out
              </button>
            </div>
          </div>
        ) : (
          <div className="border-t border-primary-700 pt-4 pb-3 px-4">
            <button
              onClick={onLogin}
              className="w-full bg-white text-primary-600 px-4 py-2 rounded-md font-medium hover:bg-gray-100 transition-colors duration-200"
            >
              Login
            </button>
          </div>
        )}
      </div>
    </nav>
  );
};

export default NavBar;
