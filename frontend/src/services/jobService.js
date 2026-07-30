import api from './api';

export const getAllJobs = async (page = 0, size = 10) => {
  const response = await api.get(`/jobs?page=${page}&size=${size}`);
  return response.data;
};

export const getJobById = async (id) => {
  const response = await api.get(`/jobs/${id}`);
  return response.data;
};

export const applyToJob = async (jobId) => {
  const response = await api.post(`/applications/apply/${jobId}`);
  return response.data;
};