# Job Board

A robust, production-ready job board application that connects job seekers with employers through advanced search, filtering, and real-time updates. Built with industry-standard technologies like Next.js and Spring Boot, it demonstrates full-stack proficiency, modern software architecture, and a focus on user-centric experiences.

---

## 🚀 Overview

This project facilitates job postings and searches with features expected in modern employment platforms:

- **Employers** can post, manage, and track jobs.
- **Job seekers** can filter, search, and apply for jobs using a seamless, intuitive interface.
- Secure authentication, full-text search, real-time notifications, and RESTful APIs for easy integrations.

---

## 🌟 Key Features

- **Authentication & Authorization:** Spring Security and OAuth2 for secure access.
- **Advanced Job Search:** Full-text search using Hibernate Search (Lucene) and multi-faceted filtering.
- **Job Management:** Employers can create, update, delete, or close job posts.
- **User Profiles:** Distinct flows for recruiters and job seekers.
- **Application Workflow:** Apply, track status, and receive email notifications.
- **Like & Bookmark:** Save and like jobs for future reference.
- **Rich Email Notifications:** Transactional and status emails via JTE templates.
- **Pagination & Sorting:** Efficient data handling for large datasets.
- **Interactive API Docs:** Explore and test endpoints via Swagger UI.

---

## 🛠️ Tech Stack

- **Backend:** Spring Boot, Spring Security, Spring Data JPA, Hibernate Search (Lucene)
- **Frontend:** Next.js 15, React 18, ShadCN UI  
  👉 [View the Frontend Repository: osteensouthpaw/job-portal-frontend](https://github.com/osteensouthpaw/job-portal-frontend)
- **Database:** PostgreSQL
- **Email & Templates:** Spring Boot Mail, JTE (Just Template Engine)
- **Build Tool:** Maven
- **Libraries:** Apache Commons Lang3, Spring Boot Actuator


## ⚡ Quick Start

### Prerequisites

- Java 23+
- PostgreSQL
- Maven

### Setup & Installation

```bash
git clone https://github.com/osteensouthpaw/job-board.git
cd job-board
# Backend setup
cd backend
cp .env.example .env
mvn clean install
# Frontend setup
cd ../frontend
cp .env.example .env
npm install
npm run dev
```

### Configuration

- Configure environment variables in both backend and frontend as needed.
- Set up PostgreSQL database and update connection strings.

---

## 📖 API Documentation

The backend REST API is fully documented with Swagger (OpenAPI).  
You can explore all available endpoints, request/response formats, and try out the API interactively.

- **Swagger UI (Production):** [https://job-board-api-v3as.onrender.com/swagger-ui.html](https://job-board-api-v3as.onrender.com/swagger-ui.html)
- **Local Development:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

You can use Swagger to:
- Browse all REST endpoints
- View request/response schemas
- Try out endpoints directly from your browser

*Example screenshot:*

![Swagger UI Example](docs/swagger-screenshot.png)


## 📚 Project Structure

```
job-board/
  backend/    # Spring Boot API (Java)
  frontend/   # Next.js (React)
  ...
```

---

## 🤝 Contributing

Open to suggestions, improvements, and collaboration!
