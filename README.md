# Job Portal

## Overview

**Job Portal** is a Spring Boot application designed to facilitate job postings and searches. It allows employers to
post job listings and job seekers to find jobs based on various filters, including full-text search powered by Hibernate
Search (Lucene).

## Features

- **User Authentication & Security:** Leverages Spring Security and OAuth2 Resource Server.
- **Job Posting & Management:** Create, update, delete, and view job posts.
- **Full-Text Job Search:** Advanced search capabilities using Hibernate Search (Lucene).
- **Pagination, Filtering & Sorting:** Efficient data retrieval for large datasets.
- **Email Notifications:** Integrated with Spring Boot Mail and JTE for rendering beautiful emails.
- **RESTful API:** Designed for seamless integration with frontend applications.


## Tech Stack

- **Backend:** Spring Boot, Spring Security, Spring Data JPA, Hibernate Search (Lucene)
- **Frontend** Nextjs 15, React 18, ShadCN UI
- **Database:** PostgreSQL
- **Template Engine:** JTE (Just Template Engine)
- **Build Tool:** Maven
- **Libraries:** Apache Commons Lang3, Spring Boot Mail, Spring Boot Actuator

## Prerequisites

- Java 23
- PostgreSQL
- Maven

## Setup & Installation

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/job-portal.git
cd job-portal
