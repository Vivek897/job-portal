import { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import api from '../services/api';
import { useAuth } from '../context/AuthContext';

function MyJobs() {
  const [jobs, setJobs] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const { user, logout } = useAuth();

  useEffect(() => {
    fetchMyJobs();
  }, []);

  const fetchMyJobs = async () => {
    try {
      const response = await api.get('/jobs/my-jobs');
      setJobs(response.data);
    } catch (err) {
      setError('Failed to load your jobs');
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (jobId) => {
    if (!confirm('Are you sure you want to delete this job?')) return;
    try {
      await api.delete(`/jobs/${jobId}`);
      setJobs(jobs.filter((job) => job.id !== jobId));
    } catch (err) {
      setError('Failed to delete job');
    }
  };

  const handleLogout = () => {
     logout();
    window.location.href = '/login';
  };

  if (loading) return <div className="text-center mt-10">Loading your jobs...</div>;

  return (
    <div className="min-h-screen bg-gray-100 p-6">
      <div className="max-w-4xl mx-auto">
        <div className="flex justify-between items-center mb-6">
          <h1 className="text-2xl font-bold text-gray-800">My Posted Jobs</h1>
          <div className="flex items-center gap-4">
            <span className="text-gray-600">Hi, {user?.name}</span>
            <Link
              to="/post-job"
              className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 transition text-sm"
            >
              + Post New Job
            </Link>
            <button onClick={handleLogout} className="text-red-600 hover:underline text-sm">
              Logout
            </button>
          </div>
        </div>

        {error && (
          <div className="bg-red-100 text-red-700 p-3 rounded mb-4">{error}</div>
        )}

        {jobs.length === 0 ? (
          <p className="text-gray-600">You haven't posted any jobs yet.</p>
        ) : (
          <div className="space-y-4">
            {jobs.map((job) => (
              <div key={job.id} className="bg-white p-5 rounded-lg shadow-md">
                <div className="flex justify-between items-start">
                  <div>
                    <h2 className="text-xl font-semibold text-gray-800">{job.title}</h2>
                    <p className="text-gray-600 text-sm mt-1">
                      {job.location} · {job.jobType} · Status: {job.status}
                    </p>
                  </div>
                  <span className="text-xs bg-gray-100 text-gray-600 px-2 py-1 rounded">
                    {job.status}
                  </span>
                </div>
                <p className="text-gray-700 mt-2 text-sm">{job.description}</p>
                <div className="flex gap-3 mt-4">
                  <button
                    onClick={() => navigate(`/job-applicants/${job.id}`)}
                    className="bg-green-600 text-white px-3 py-1.5 rounded text-sm hover:bg-green-700 transition"
                  >
                    View Applicants
                  </button>
                  <button
                    onClick={() => handleDelete(job.id)}
                    className="bg-red-100 text-red-700 px-3 py-1.5 rounded text-sm hover:bg-red-200 transition"
                  >
                    Delete
                  </button>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}

export default MyJobs;