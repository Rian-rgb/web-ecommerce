import http from 'k6/http';
import {check, sleep} from 'k6';
import {Rate} from 'k6/metrics';

export const options = {
    vus: 5, // 5 virtual users
    duration: '1m',
};

const BASE_URL = 'http://localhost:3000';
const PAGE_SIZE = 20;

export default function () {
    const url = `${BASE_URL}/api/v1/products?page=0&size=${PAGE_SIZE}`;

    const params = {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer eyJhbGciOiJIUzM4NCJ9.eyJyb2xlIjpbIlJPTEVfQURNSU4iXSwic3ViIjoiYnJpbGwiLCJpYXQiOjE3MzUzOTg5MzQsImV4cCI6MTczNTQ4NTMzNH0.ZS2b0lpj8GrWGLqTTK693zkmzPkT-PhI_WtkYcwQMmmNL0TypJHlU6j9JM56ZIaI',
        },
    };

    const response = http.get(url, params);

    // Check the response
    check(response, {
        'status is 200': (r) => r.status == 200,
        'rate limit not exceeded': (r) => r.status != 429,
    });

    // Log the response status and time
    console.log(`Status: ${response.status}, Response time: ${response.timings.duration} ms`);

    // Short pause between requests
    sleep(0.1);
}