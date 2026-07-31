import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import Login from './pages/Login';
import Register from './pages/Register';
import Jobs from './pages/Jobs';
import MyApplications from './pages/MyApplications';
import PostJob from './pages/PostJob';
import MyJobs from './pages/MyJobs';
import JobApplicants from './pages/JobApplicants';
function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/job-applicants/:jobId" element={<JobApplicants />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/jobs" element={<Jobs />} />
        <Route path="/my-applications" element={<MyApplications />} />
        <Route path="/post-job" element={<PostJob />} />
        <Route path="/my-jobs" element={<MyJobs />} />
        <Route path="/" element={<Navigate to="/login" />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;