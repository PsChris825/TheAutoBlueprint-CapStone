# Car Blueprint

Car Blueprint is a full-stack application designed for car enthusiasts to plan, track, and manage vehicle modifications. Users can create detailed modification plans for their cars, document progress, and manage associated parts, suppliers, and budgets. The app also allows users to interact with a community of fellow car enthusiasts, sharing their builds, receiving feedback, and linking helpful resources like tutorial videos.

This project utilizes MySQL for database management, storing user profiles, modification plans, parts, and supplier information. The back end is built with Java Spring Boot, enabling CRUD operations, user authentication, and integration with external APIs to pull vehicle and parts data. The front end, developed with React.js, features Tailwind CSS for responsive design and SVG-enhanced UI components. Core features include modification planning, part management, budget tracking, and a community feedback system.

## Technical Details

### Backend

- **Database**: MySQL
  - Tables:
    - `Users`: Stores user credentials and authentication data.
    - `ModificationPlans`: Tracks vehicle modification details, timelines, and budgets.
    - `Parts`: Holds information about car parts, including prices and supplier details.
    - `Suppliers`: Manages supplier details and links them to the relevant parts.
    - `Posts`: User-generated posts for sharing modification plans and receiving feedback.
    - `Comments`: Stores user comments on posts.
- **API**: Java Spring Boot
  - **User Authentication**: Handles user registration and login, including secure password hashing.
  - **Modification Plans**: Users can create, update, and track vehicle modification plans, including vehicle selection, part details, and progress timelines.
  - **Parts and Suppliers**: Integrated with external APIs to fetch vehicle details, parts, and supplier information.
  - **Community Interaction**: Users can post updates, share their builds, receive feedback, and engage in discussions.
  
### Frontend

- **React.js** with **Tailwind CSS**
  - **UI Components**:
    - `RegisterForm`: User registration with email and password.
    - `LoginForm`: User login form.
    - `ModificationPlanForm`: Form to create and update modification plans, including vehicle selection, parts, budget, and timeline.
    - `PartSearch`: Interface for users to search and browse car parts from external APIs.
    - `PostList`: Displays user posts and allows interaction via comments.
  - Features:
    - Real-time data updates and form validation.
    - Responsive design using Tailwind CSS for optimal experience on any device.
    - SVG icons for enhanced visual design.
  
### Security

- **Password Hashing**: Secure hashing and validation of passwords before storing them in the database.
  
### API Integrations

- **Vehicle and Parts API**: Integrated external APIs for fetching car models, parts, and supplier information to improve user experience when creating modification plans.

---

