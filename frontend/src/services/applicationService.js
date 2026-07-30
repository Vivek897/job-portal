import api from './api';

export const getMyApplications = async () => {
  const response = await api.get('/applications/my-applications');
  return response.data;
};