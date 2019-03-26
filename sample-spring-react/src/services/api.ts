import axios from 'axios';
import { config } from '../config';

const apiInstance = axios.create({
    baseURL: config.apiPrefix,
    withCredentials: true
});

apiInstance.interceptors.response.use((response) => {
    return response;
}, (error) => {
    return Promise.reject(error.response.data);
});

export default apiInstance;
