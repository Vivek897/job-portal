import { useState, useEffect } from 'react';
import { getAllJobs, applyToJob } from '../services/jobService';
import { Link } from 'react-router-dom';


function Jobs() {
  const [jobs, setJobs] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [message, setMessage] = useState('');

  const user = JSON.parse(localStorage.getItem('user'));

  useEffect(() => {
    fetchJobs();
  }, []);

  const fetchJobs = async () => {
    try {
      const data = await getAllJobs();
      setJobs(data.content);
    } catch (err) {
      setError('Failed to load jobs');
    } finally {
      setLoading(false);
    }
  };

  const handleApply = async (jobId) => {
    setMessage('');
    setError('');
    try {
      await applyToJob(jobId);
      setMessage('Applied successfully!');
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to apply');
    }
  };

  const handleLogout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    window.location.href = '/login';
  };

  if (loading) return <div className="text-center mt-10">Loading jobs...</div>;

  return (
    <div className="min-h-screen bg-gray-100 p-6">
      <div className="max-w-4xl mx-auto">
        <div className="flex justify-between items-center mb-6">
          <h1 className="text-2xl font-bold text-gray-800">Available Jobs</h1>
          <div className="flex items-center gap-4">
           <span className="text-gray-600">Hi, {user?.name}</span>
{user?.role === 'CANDIDATE' && (
  <Link to="/my-applications" className="text-blue-600 hover:underline text-sm">
    My Applications
  </Link>
)}
            <button
              onClick={handleLogout}
              className="text-red-600 hover:underline text-sm"
            >
              Logout
            </button>
          </div>
        </div>

        {message && (
          <div className="bg-green-100 text-green-700 p-3 rounded mb-4">
            {message}
          </div>
        )}
        {error && (
          <div className="bg-red-100 text-red-700 p-3 rounded mb-4">
            {error}
          </div>
        )}

        {jobs.length === 0 ? (
          <p className="text-gray-600">No jobs available right now.</p>
        ) : (
          <div className="space-y-4">
            {jobs.map((job) => (
              <div key={job.id} className="bg-white p-5 rounded-lg shadow-md">
                <h2 className="text-xl font-semibold text-gray-800">{job.title}</h2>
                <p className="text-gray-600 mt-1">{job.companyName} · {job.location}</p>
                <p className="text-gray-700 mt-2">{job.description}</p>
                <div className="flex flex-wrap gap-2 mt-3">
                  {job.requiredSkills.split(',').map((skill) => (
                    <span
                      key={skill}
                      className="bg-blue-100 text-blue-700 text-xs px-2 py-1 rounded"
                    >
                      {skill.trim()}
                    </span>
                  ))}
                </div>
                <div className="flex justify-between items-center mt-4">
                  <span className="text-gray-600 text-sm">
                    ₹{job.salaryMin} - ₹{job.salaryMax} · {job.jobType}
                  </span>
                  {user?.role === 'CANDIDATE' && (
                    <button
                      onClick={() => handleApply(job.id)}
                      className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 transition"
                    >
                      Apply Now
                    </button>
                  )}
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}

export default Jobs;
