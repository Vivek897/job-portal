import { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import api from '../services/api';

const statusColors = {
  APPLIED: 'bg-yellow-100 text-yellow-700',
  SHORTLISTED: 'bg-blue-100 text-blue-700',
  REJECTED: 'bg-red-100 text-red-700',
  HIRED: 'bg-green-100 text-green-700',
};

function JobApplicants() {
  const { jobId } = useParams();
  const [applicants, setApplicants] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [updating, setUpdating] = useState(null);

  useEffect(() => {
    fetchApplicants();
  }, [jobId]);

  const fetchApplicants = async () => {
    try {
      const response = await api.get(`/applications/job/${jobId}`);
      setApplicants(response.data);
    } catch (err) {
      setError('Failed to load applicants');
    } finally {
      setLoading(false);
    }
  };

  const handleStatusUpdate = async (applicationId, newStatus) => {
    setUpdating(applicationId);
    try {
      await api.put(`/applications/${applicationId}/status`, { status: newStatus });
      setApplicants(
        applicants.map((app) =>
          app.id === applicationId ? { ...app, status: newStatus } : app
        )
      );
    } catch (err) {
      setError('Failed to update status');
    } finally {
      setUpdating(null);
    }
  };

  if (loading) return <div className="text-center mt-10">Loading applicants...</div>;

  return (
    <div className="min-h-screen bg-gray-100 p-6">
      <div className="max-w-3xl mx-auto">
        <div className="flex justify-between items-center mb-6">
          <h1 className="text-2xl font-bold text-gray-800">Applicants</h1>
          <Link to="/my-jobs" className="text-blue-600 hover:underline text-sm">
            ← Back to My Jobs
          </Link>
        </div>

        {error && (
          <div className="bg-red-100 text-red-700 p-3 rounded mb-4">{error}</div>
        )}

        {applicants.length === 0 ? (
          <p className="text-gray-600">No applicants yet for this job.</p>
        ) : (
          <div className="space-y-4">
            {applicants.map((app) => (
              <div key={app.id} className="bg-white p-5 rounded-lg shadow-md">
                <div className="flex justify-between items-start">
                  <div>
                    <h2 className="text-lg font-semibold text-gray-800">
                      {app.candidateName}
                    </h2>
                    <p className="text-gray-600 text-sm mt-1">{app.candidateEmail}</p>
                    <p className="text-gray-500 text-sm mt-1">
                      Applied on {new Date(app.appliedAt).toLocaleDateString()}
                    </p>
                  </div>
                  <span
                    className={`text-xs px-3 py-1 rounded-full font-medium ${statusColors[app.status]}`}
                  >
                    {app.status}
                  </span>
                </div>

                <div className="flex gap-2 mt-4">
                  <button
                    disabled={updating === app.id || app.status === 'SHORTLISTED'}
                    onClick={() => handleStatusUpdate(app.id, 'SHORTLISTED')}
                    className="bg-blue-100 text-blue-700 px-3 py-1.5 rounded text-sm hover:bg-blue-200 transition disabled:opacity-50"
                  >
                    Shortlist
                  </button>
                  <button
                    disabled={updating === app.id || app.status === 'HIRED'}
                    onClick={() => handleStatusUpdate(app.id, 'HIRED')}
                    className="bg-green-100 text-green-700 px-3 py-1.5 rounded text-sm hover:bg-green-200 transition disabled:opacity-50"
                  >
                    Hire
                  </button>
                  <button
                    disabled={updating === app.id || app.status === 'REJECTED'}
                    onClick={() => handleStatusUpdate(app.id, 'REJECTED')}
                    className="bg-red-100 text-red-700 px-3 py-1.5 rounded text-sm hover:bg-red-200 transition disabled:opacity-50"
                  >
                    Reject
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

export default JobApplicants;