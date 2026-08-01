# Job Portal - Full Stack Application

A full-stack job portal where candidates can search and apply for jobs, and recruiters can post jobs and manage applications.

## 🚀 Tech Stack

**Frontend:**
- React (Vite)
- Tailwind CSS
- React Router DOM
- Axios

**Backend:**
- Spring Boot
- Spring Security (JWT Authentication)
- Spring Data JPA
- PostgreSQL (hosted on Supabase)

## ✨ Features

### Candidate
- Register/Login with role-based authentication
- Browse all open job listings
- Apply to jobs (one application per job)
- Track application status (Applied, Shortlisted, Rejected, Hired)

### Recruiter
- Register/Login with role-based authentication
- Post new job listings
- View and manage posted jobs
- View applicants for each job
- Update application status

### Security
- JWT-based authentication and authorization
- Password encryption using BCrypt
- Role-based access control (Candidate vs Recruiter)
- Protected API routes

## 📁 Project Structure
## 🔧 Setup Instructions

### Prerequisites
- Java 17+
- Node.js (LTS)
- PostgreSQL database (or Supabase account)

### Backend Setup
1. Navigate to the backend folder:
2. Copy `application.properties.example` to `application.properties` and fill in your database credentials and JWT secret.
3. Run the application:
Backend runs on `http://localhost:8080`

### Frontend Setup
1. Navigate to the frontend folder:
2. Install dependencies:
3. Start the development server:
Frontend runs on `http://localhost:5173`

## 📡 API Endpoints

### Auth
| Method | Endpoint | Description |
|--------|----------|--------------|
| POST | `/api/auth/register` | Register a new user |
| POST | `/api/auth/login` | Login and receive JWT token |

### Jobs
| Method | Endpoint | Description | Access |
|--------|----------|--------------|--------|
| GET | `/api/jobs` | Get all open jobs (paginated) | Public |
| GET | `/api/jobs/{id}` | Get job by ID | Public |
| POST | `/api/jobs` | Create a new job | Recruiter |
| GET | `/api/jobs/my-jobs` | Get jobs posted by recruiter | Recruiter |
| PUT | `/api/jobs/{id}` | Update a job | Recruiter |
| DELETE | `/api/jobs/{id}` | Delete a job | Recruiter |

### Applications
| Method | Endpoint | Description | Access |
|--------|----------|--------------|--------|
| POST | `/api/applications/apply/{jobId}` | Apply to a job | Candidate |
| GET | `/api/applications/my-applications` | Get candidate's applications | Candidate |
| GET | `/api/applications/job/{jobId}` | Get applicants for a job | Recruiter |
| PUT | `/api/applications/{id}/status` | Update application status | Recruiter |

## 👤 Author

Vivek Kumar Singh

## 📝 License

This project is for portfolio/educational purposes.