# The Car Blueprint Proposal

## Problem Statement

Car enthusiasts often struggle to keep track of and manage modifications to their vehicles. Planning and documenting modifications can become overwhelming, especially when managing parts, suppliers, and progress updates. There is no centralized platform that helps users plan their modifications, track progress, and engage with a community of car enthusiasts for feedback and recommendations.

## Technical Solution

Develop a Car Modification Planner application that allows users to create, manage, and document modifications to their vehicles. This app will provide a centralized place for users to plan modifications, track progress, and interact with the car enthusiast community. Key features include:

- **Modification Plans**: Users can create detailed plans for their vehicle modifications, including descriptions, timelines, and progress tracking.
- **Part Database**: A searchable database of car parts and suppliers will help users find the components they need.
  - Users will select parts from a drop-down menu or add custom parts not in the system.
  - Users can specify tools needed for each part and adjust prices based on their preferred suppliers.
- **Community Feedback**: Users can share their modification plans and progress updates to receive feedback and advice from other car enthusiasts.
- **Supplier Interaction**: Users can view supplier information, manage orders, and track delivery statuses.
- **Budget Planner**: Users can see their entire build laid out and manage their budget accordingly.
- **Stretch Goals**: Users will be able to provide feedback to others, contributing to an auto-populated database of parts for specific cars.

### Scenario 1

John is a car enthusiast who wants to upgrade his vehicle's suspension system. He uses the Car Modification Planner app to create a detailed plan for the modification, including a list of required parts and suppliers. He tracks the progress of the modification, shares updates with the community, and receives feedback on his choices. The app also helps him manage his budget and link tutorial videos to assist in various parts of the build.

### Scenario 2

Emily has just bought a new car and wants to customize it with various modifications. She uses the Car Modification Planner app to explore different modification options and create a plan for her upgrades. She browses the part database, checks supplier ratings, and tracks the status of her orders. Emily interacts with other users to get recommendations and see how similar modifications have turned out for others.

## Glossary

- **Modification Plan**: A detailed description of the planned changes to a vehicle, including parts to be used, timelines, budget, and progress tracking.
- **Part Database**: A collection of car parts available for users to input extra details in for their specific builds.
- **Progress Tracking**: The ability to monitor the status and completion of modifications over time.
- **Community Feedback**: Input and advice from other car enthusiasts regarding modification plans and progress.
- **User Profile**: Information about the user, including their vehicle details, modification history, and community interactions.

## User Stories/Scenarios

### Create an Account

- **Description**: As a casual user, I want to create an account with my email and password so that I can log in and gain access to authenticated features.
- **Suggested Data**: Email, password.
- **Precondition**: None.
- **Post-condition**: The user is registered and can log in to access authenticated features.

#### Technical Details

**Database:**
- **Table**: `Users`
  - **Columns**: `user_id` (PK), `email` (unique), `password_hash`

**REST API:**
- **Model**: `User`
- **Repository**: `UserRepository` (CRUD operations)
- **Service**: `UserService` (User registration, authentication)
- **Controller**: `UserController` (Endpoints for registration and login)

**UI:**
- **Component**: `RegisterForm` (Email, password input fields, submit button)
- **Component**: `LoginForm` (Email, password input fields, submit button)

**Other Details:**
- **Validation**: Ensure unique email, validate password strength.
- **Security**: Hash passwords before storing them in the database.

### Create a Modification Plan

- **Description**: As an authenticated user, I want to create a new modification plan for my vehicle by choosing my car's year, make, and model. I will then add detailed plans for modifications, including parts needed, their prices, and links to tutorials or suppliers. I want to create a timeline for completing each step of the modification.

#### Technical Details

**Database:**
- **Tables**:
  - `ModificationPlans`
    - **Columns**:
      - `plan_id` (PK): Unique identifier for each modification plan.
      - `user_id` (FK): Foreign key to link the plan to the user who created it.
      - `vehicle_year`: The year of the vehicle.
      - `vehicle_make`: The make (brand) of the vehicle.
      - `vehicle_model`: The model of the vehicle.
      - `description`: Detailed description of the modification plan.
      - `timeline`: JSON or text field to store the timeline information, such as start dates and completion dates for each modification step.
      - `budget`: Total budget allocated for the modifications.
      - `created_at`: Timestamp of when the plan was created.
      - `updated_at`: Timestamp of the last update to the plan.
  - `Parts`
    - **Columns**:
      - `id` (PK): Unique identifier for each part entry.
      - `plan_id` (FK): Foreign key to link the part to a specific modification plan.
      - `part_name`: Name of the car part.
      - `part_number`: Unique identifier for the part (e.g., SKU or catalog number).
      - `manufacturer`: The manufacturer of the part.
      - `OEM_number`: Original Equipment Manufacturer number, if available.
      - `weight`: Weight of the part.
      - `details`: Additional details about the part (e.g., specifications).
      - `price`: Price of the part, entered by the user.
      - `supplier_info`: URLs or contact information for suppliers where the part can be purchased.
      - `fits_on_cars`: List of car models or makes that the part is compatible with.
  - `Tutorials`
    - **Columns**:
      - `id` (PK): Unique identifier for each tutorial entry.
      - `plan_id` (FK): Foreign key to link the tutorial to a specific modification plan.
      - `video_link`: URL of the tutorial video.
      - `description`: Brief description of what the tutorial covers.

**REST API:**
- **Model**:
  - `ModificationPlan`: Represents a modification plan including vehicle details, description, timeline, and budget.
  - `Part`: Represents a part needed for the modification, including its name, part number, manufacturer, OEM number, weight, details, price, and supplier information.
  - `Tutorial`: Represents a tutorial or guide linked to the modification plan.
- **Repository**:
  - `ModificationPlanRepository`: CRUD operations for modification plans.
  - `PartRepository`: CRUD operations for parts.
  - `TutorialRepository`: CRUD operations for tutorials.
- **Service**:
  - `ModificationPlanService`: Handles business logic for creating and updating modification plans, including interacting with the external API to pull vehicle information.
  - `PartService`: Manages part-related operations, including adding parts to the plan and calculating total costs.
  - `TutorialService`: Manages tutorials associated with the plan.
- **Controller**:
  - `ModificationPlanController`: API endpoints for creating, retrieving, updating, and deleting modification plans. Includes endpoints for pulling vehicle information from the external API.
  - `PartController`: API endpoints for managing parts.
  - `TutorialController`: API endpoints for managing tutorials.

**UI:**
- **Component**: `ModificationPlanForm`
  - **Fields**:
    - **Vehicle Selection**: Dropdowns or search fields to choose year, make, and model of the car (data pulled from an external API).
    - **Modification Details**: Text area for entering a description of the modification plan.
    - **Parts Section**:
      - Input fields for part name, part number, manufacturer, OEM number, weight, details, price, and supplier URLs.
      - Ability to search for parts using an external API.
      - Dropdown or multi-select input for specifying which car models the part fits.
    - **Timeline Section**:
      - Inputs for start dates, end dates, and estimated hours for each modification step.
    - **Submit Button**: To save the modification plan to the database.
- **Component**: `PlanDetails`
  - **Displays**:
    - Vehicle details, modification description, parts with their details and prices, tutorials, and timeline.
    - Allows users to view and edit existing plans.

**Other Details:**
- **API Integration**: Integration with an external API to fetch car details (year, make, model) to help users select their vehicle and parts.
- **Validation**:
  - Ensure all required fields are filled out.
  - Validate that the vehicle details are correctly selected.
  - Check that the timeline dates are in the correct order (start date before end date).
- **Complex Rules**:
  - **Budget Constraints**: Ensure that the total budget is sufficient for all selected parts and any additional costs. Show how much is left in budget compared to cost.
- **Calculations**:
  - **Total Cost**: Calculate the total cost of the modification plan based on the individual parts' prices.


### Edit a Modification Plan

- **Description**: As an authenticated user, I want to modify an existing plan to update details or adjust progress.
- **Precondition**: User must be logged in. The plan must not be completed.
- **Post-condition**: The modification plan is updated with new details.

#### Technical Details

**Database:**
- **Same Tables** as `Create a Modification Plan`
  - Update existing entries based on `id`

**REST API:**
- **Service**: `ModificationPlanService` (Plan updates)
- **Controller**: `ModificationPlanController` (Endpoint for updating plans)

**UI:**
- **Component**: `ModificationPlanEditForm` (Editable form for modifying existing plans)

**Other Details:**
- **Validation**: Ensure updated details are consistent and complete.

### Track Progress

- **Description**: As an authenticated user, I want to update and track the progress of my modification plans.
- **Precondition**: User must be logged in. The plan must be in progress.
- **Post-condition**: The progress is updated and reflected in the plan status.

#### Technical Details

**Database:**
- **Table**: `ModificationPlans`
  - Add column `progress` or similar to track status

**REST API:**
- **Service**: `ModificationPlanService` (Progress updates)
- **Controller**: `ModificationPlanController` (Endpoint for updating progress)

**UI:**
- **Component**: `ProgressTracker` (UI for updating and viewing progress)

**Other Details:**
- **Complex Rules**: Define valid progress states and update accordingly.

### Browse and Search Parts

- **Description**: As a casual user, I want to search for parts in the part database. I can add details specific to my build, including tools needed and prices from suppliers. I can also tag tutorial videos to parts and provide links to suppliers. The parts list will auto-populate using the API based on the car that I select.

- **Precondition**: None.

- **Post-condition**: Search results display relevant parts and can be updated with specific details.

#### Technical Details

**Database:**
- **Table**: `Parts`
  - **Columns**:
    - `id` (PK): Unique identifier for each part entry.
    - `plan_id` (FK): Foreign key to link the part to a specific modification plan.
    - `part_name`: Name of the car part.
    - `part_number`: Unique identifier for the part (e.g., SKU or catalog number).
    - `manufacturer`: The manufacturer of the part.
    - `OEM_number`: Original Equipment Manufacturer number, if available.
    - `weight`: Weight of the part.
    - `details`: Additional details about the part (e.g., specifications).
    - `price`: Price of the part, entered by the user.
    - `supplier_info`: URLs or contact information for suppliers where the part can be purchased.
    - `fits_on_cars`: List of car models or makes that the part is compatible with.
    - `tools_needed`: List of tools required for installation.
    - `supplier_link`: URL link to the supplier or store where the part can be purchased.

**REST API:**
- **Model**: `Part`
  - Includes fields for part name, part number, manufacturer, OEM number, weight, details, price, supplier information, tools needed, and supplier link.
- **Repository**: `PartRepository`
  - **Functions**: Search and retrieval of parts based on various criteria (e.g., vehicle details, part name).
- **Service**: `PartService`
  - **Functions**: Handles part details and search functionality, including integrating with the external API to auto-populate parts based on the car selected.
- **Controller**: `PartController`
  - **Endpoints**: Provides endpoints for searching parts, retrieving part details, and managing part information.

**UI:**
- **Component**: `PartSearch`
  - **Features**:
    - **Search Form**: Allows users to input search criteria (e.g., part name, vehicle details).
    - **Auto-Population**: Fetches and displays parts based on the car selected, using data from the external API.
    - **Results Display**: Shows search results with the ability to filter and sort.
- **Component**: `PartDetails`
  - **Displays**:
    - Detailed view of a selected part, including tools needed, price, and supplier links.
    - Option to tag tutorial videos related to the part.

**Other Details:**
- **API Integration**: Integration with an external API to fetch and auto-populate parts based on the selected vehicle's year, make, and model.
- **Complex Rules**:
  - STRETCH **Search Filters**: Implement filters for narrowing search results based on criteria such as part type, price range, or vehicle compatibility.
  - STRETCH **Sorting**: Allow sorting of search results by different attributes (e.g., price, popularity).


### Share and Receive Feedback

- **Description**: As an authenticated user, I want to share my modification plans, build photos, or individual parts with the community for feedback. I can post pictures and write descriptions to ask questions or provide updates. Other users can view these posts, comment on them, and engage in discussions.

- **Precondition**: User must be logged in.

- **Post-condition**: The shared post is visible to other users, and feedback can be provided. Other users can also post their own comments and descriptions.

#### Technical Details

**Database:**
- **Table**: `Posts`
  - **Columns**:
    - `id` (PK): Unique identifier for each post.
    - `user_id` (FK): Foreign key to link the post to the user who created it.
    - `title`: Title of the post.
    - `description`: Description or content of the post.
    - `image_url`: URL of the image or photo associated with the post (optional).
    - `created_at`: Timestamp of when the post was created.
    - `updated_at`: Timestamp of the last update to the post.

- **Table**: `Comments`
  - **Columns**:
    - `id` (PK): Unique identifier for each comment.
    - `post_id` (FK): Foreign key to link the comment to a specific post.
    - `user_id` (FK): Foreign key to link the comment to the user who made it.
    - `comment_text`: Text of the comment.
    - `created_at`: Timestamp of when the comment was made.

**REST API:**
- **Model**: `Post`, `Comment`
  - **Post**: Represents a forum post including title, description, and image.
  - **Comment**: Represents a comment on a post.
- **Repository**: `PostRepository`, `CommentRepository`
  - **Functions**: CRUD operations for posts and comments.
- **Service**: `PostService`, `CommentService`
  - **Functions**: Handles business logic for creating, updating, and retrieving posts and comments.
- **Controller**: `PostController`, `CommentController`
  - **Endpoints**: Provides endpoints for creating, retrieving, updating, and deleting posts and comments.

**UI:**
- **Component**: `PostForm`
  - **Features**:
    - **Input Fields**: For title, description, and uploading images.
    - **Submit Button**: To post the content to the forum.
- **Component**: `PostList`
  - **Displays**:
    - List of forum posts, including titles, descriptions, and images.
    - Links to view detailed post content and associated comments.
- **Component**: `PostDetails`
  - **Displays**:
    - Full details of a selected post, including the image and description.
    - **Comments Section**: List of comments with the ability to add new comments.
- **Component**: `CommentForm`
  - **Features**:
    - **Input Field**: For writing and submitting comments.
    - **Submit Button**: To post the comment on the selected post.

**Other Details:**
- **Additional Features**:
  - **Image Handling**: Efficiently handle image uploads and storage.
  - STRETCH **User Notifications**: Notify users of new comments on their posts or replies to their comments.


### View Supplier Information

- **Description**: As a casual user, I want to view and manage supplier information for parts I need and access supplier websites for purchasing.
- **Precondition**: None.
- **Post-condition**: Supplier details are displayed to the user.

#### Technical Details

**Database:**
- **Table**: `Suppliers`
  - **Columns**: `id` (PK), `name`, `contact_info`, `website`, `part_ids` (relation to `Parts`)

**REST API:**
- **Model**: `Supplier`
- **Repository**: `SupplierRepository`
- **Service**: `SupplierService`
- **Controller**: `SupplierController` (Endpoint for supplier information)

**UI:**
- **Component**: `SupplierInfo` (Display supplier details and links)

**Other Details:**
- **Complex Rules**: Ensure up-to-date supplier information.

# Detailed Technical Task Plan

## Data Model and Database Schema

1. **Design Database Schema for Users Table**
  - **Task 1.1**: Define schema requirements (fields, data types, constraints).
  - **Task 1.2**: Create `Users` table in the database.
  - **Task 1.3**: Verify schema creation with basic queries.
  - **Estimated Time**: 0.5 hours
  - **Testing**:
    - **Task**: Write tests to ensure the table structure is correct.
    - **Estimated Time**: 0.5 hours

2. **Design Database Schema for ModificationPlans Table**
  - **Task 2.1**: Define schema requirements (fields, data types, constraints).
  - **Task 2.2**: Create `ModificationPlans` table in the database.
  - **Task 2.3**: Verify schema creation with basic queries.
  - **Estimated Time**: 0.5 hours
  - **Testing**:
    - **Task**: Write tests to ensure the table structure is correct.
    - **Estimated Time**: 0.5 hours

3. **Design Database Schema for Parts Table**
  - **Task 3.1**: Define schema requirements (fields, data types, constraints).
  - **Task 3.2**: Create `Parts` table in the database.
  - **Task 3.3**: Verify schema creation with basic queries.
  - **Estimated Time**: 0.5 hours
  - **Testing**:
    - **Task**: Write tests to ensure the table structure is correct.
    - **Estimated Time**: 0.5 hours

4. **Design Database Schema for Tutorials Table**
  - **Task 4.1**: Define schema requirements (fields, data types, constraints).
  - **Task 4.2**: Create `Tutorials` table in the database.
  - **Task 4.3**: Verify schema creation with basic queries.
  - **Estimated Time**: 0.5 hours
  - **Testing**:
    - **Task**: Write tests to ensure the table structure is correct.
    - **Estimated Time**: 0.5 hours

5. **Design Database Schema for Suppliers Table**
  - **Task 5.1**: Define schema requirements (fields, data types, constraints).
  - **Task 5.2**: Create `Suppliers` table in the database.
  - **Task 5.3**: Verify schema creation with basic queries.
  - **Estimated Time**: 0.5 hours
  - **Testing**:
    - **Task**: Write tests to ensure the table structure is correct.
    - **Estimated Time**: 0.5 hours

## Repository Layer

1. **Implement UserRepository with CRUD Operations**
  - **Task 1.1**: Define `UserRepository` interface and methods.
  - **Task 1.2**: Implement `UserRepository` using JDBC or ORM.
  - **Task 1.3**: Add methods for `findById`, `findAll`, `save`, `update`, and `delete`.
  - **Estimated Time**: 3 hours
  - **Testing**:
    - **Task**: Write unit tests for CRUD operations (mock database interactions if needed).
    - **Estimated Time**: 1 hour

2. **Implement ModificationPlanRepository with CRUD Operations**
  - **Task 2.1**: Define `ModificationPlanRepository` interface and methods.
  - **Task 2.2**: Implement `ModificationPlanRepository` using JDBC or ORM.
  - **Task 2.3**: Add methods for `findById`, `findAll`, `save`, `update`, and `delete`.
  - **Estimated Time**: 3 hours
  - **Testing**:
    - **Task**: Write unit tests for CRUD operations.
    - **Estimated Time**: 1 hour

3. **Implement PartRepository with CRUD Operations**
  - **Task 3.1**: Define `PartRepository` interface and methods.
  - **Task 3.2**: Implement `PartRepository` using JDBC or ORM.
  - **Task 3.3**: Add methods for `findById`, `findAll`, `save`, `update`, and `delete`.
  - **Estimated Time**: 3 hours
  - **Testing**:
    - **Task**: Write unit tests for CRUD operations.
    - **Estimated Time**: 1 hour

4. **Implement SupplierRepository with CRUD Operations**
  - **Task 4.1**: Define `SupplierRepository` interface and methods.
  - **Task 4.2**: Implement `SupplierRepository` using JDBC or ORM.
  - **Task 4.3**: Add methods for `findById`, `findAll`, `save`, `update`, and `delete`.
  - **Estimated Time**: 3 hours
  - **Testing**:
    - **Task**: Write unit tests for CRUD operations.
    - **Estimated Time**: 1 hour

## Service Layer

1. **Implement UserService with Registration and Login Logic**
  - **Task 1.1**: Define `UserService` interface and methods.
  - **Task 1.2**: Implement registration logic (create new user, validate input).
  - **Task 1.3**: Implement login logic (authenticate user, generate tokens).
  - **Estimated Time**: 3 hours
  - **Testing**:
    - **Task**: Write unit tests for registration and login logic.
    - **Estimated Time**: 1 hour

2. **Implement ModificationPlanService with CRUD Operations**
  - **Task 2.1**: Define `ModificationPlanService` interface and methods.
  - **Task 2.2**: Implement CRUD operations for modification plans.
  - **Task 2.3**: Handle validation and business logic.
  - **Estimated Time**: 3 hours
  - **Testing**:
    - **Task**: Write unit tests for CRUD operations.
    - **Estimated Time**: 1 hour

3. **Implement PartService with CRUD Operations**
  - **Task 3.1**: Define `PartService` interface and methods.
  - **Task 3.2**: Implement CRUD operations for parts.
  - **Task 3.3**: Handle validation and business logic.
  - **Estimated Time**: 3 hours
  - **Testing**:
    - **Task**: Write unit tests for CRUD operations.
    - **Estimated Time**: 1 hour

4. **Implement SupplierService with CRUD Operations**
  - **Task 4.1**: Define `SupplierService` interface and methods.
  - **Task 4.2**: Implement CRUD operations for suppliers.
  - **Task 4.3**: Handle validation and business logic.
  - **Estimated Time**: 3 hours
  - **Testing**:
    - **Task**: Write unit tests for CRUD operations.
    - **Estimated Time**: 1 hour

## Controller Layer

1. **Implement UserController with Registration and Login Endpoints**
  - **Task 1.1**: Define REST endpoints (`/register`, `/login`).
  - **Task 1.2**: Implement controller methods for registration and login.
  - **Task 1.3**: Connect to `UserService` and handle request/response.
  - **Estimated Time**: 3 hours
  - **Testing**:
    - **Task**: Write integration tests for registration and login endpoints.
    - **Estimated Time**: 1 hour

2. **Implement ModificationPlanController with CRUD Endpoints**
  - **Task 2.1**: Define REST endpoints (e.g., `/modification-plans`).
  - **Task 2.2**: Implement controller methods for CRUD operations.
  - **Task 2.3**: Connect to `ModificationPlanService` and handle request/response.
  - **Estimated Time**: 3 hours
  - **Testing**:
    - **Task**: Write integration tests for CRUD endpoints.
    - **Estimated Time**: 1 hour

3. **Implement PartController with CRUD Endpoints**
  - **Task 3.1**: Define REST endpoints (e.g., `/parts`).
  - **Task 3.2**: Implement controller methods for CRUD operations.
  - **Task 3.3**: Connect to `PartService` and handle request/response.
  - **Estimated Time**: 3 hours
  - **Testing**:
    - **Task**: Write integration tests for CRUD endpoints.
    - **Estimated Time**: 1 hour

4. **Implement SupplierController with CRUD Endpoints**
  - **Task 4.1**: Define REST endpoints (e.g., `/suppliers`).
  - **Task 4.2**: Implement controller methods for CRUD operations.
  - **Task 4.3**: Connect to `SupplierService` and handle request/response.
  - **Estimated Time**: 3 hours
  - **Testing**:
    - **Task**: Write integration tests for CRUD endpoints.
    - **Estimated Time**: 1 hour

## Security

1. **Implement Security for User Login**
  - **Task 1.1**: Set up security configurations (e.g., JWT, OAuth).
  - **Task 1.2**: Implement login security (hash passwords, validate tokens).
  - **Task 1.3**: Integrate with `UserService` for secure authentication.
  - **Estimated Time**: 2 hours

## Frontend UI Development

1. **Learn Tailwind CSS**
  - **Task 1.1**: Follow tutorials and documentation to understand Tailwind CSS basics.
  - **Task 1.2**: Practice applying Tailwind CSS in sample projects.
  - **Estimated Time**: 2 hours

2. **Learn SVG Basics**
  - **Task 2.1**: Follow tutorials and documentation to understand SVG basics.
  - **Task 2.2**: Practice creating and integrating SVG graphics.
  - **Estimated Time**: 2 hours

3. **Create PostForm Component with Tailwind CSS and SVG**
  - **Task 3.1**: Design and implement `PostForm` component layout.
  - **Task 3.2**: Style component using Tailwind CSS.
  - **Task 3.3**: Integrate SVG icons and graphics.
  - **Estimated Time**: 4 hours
  - **Testing**:
    - **Task**: Write and execute unit tests for form functionality and styling.
    - **Estimated Time**: 1 hour

4. **Create CommentForm Component with Tailwind CSS and SVG**
  - **Task 4.1**: Design and implement `CommentForm` component layout.
  - **Task 4.2**: Style component using Tailwind CSS.
  - **Task 4.3**: Integrate SVG icons and graphics.
  - **Estimated Time**: 4 hours
  - **Testing**:
    - **Task**: Write and execute unit tests for form functionality and styling.
    - **Estimated Time**: 1 hour

## Summary of Estimated Times

**Development Time**: 76 hours  
**Learning Time**: 6 hours  
**Testing Time**: 24 hours  
**Security Implementation Time**: 2 hours

**Total Estimated Time**: 108 hours

**Stretch Goals**: 22 hours  

# Project Schedule: August 12th - August 21st

## August 12th (Monday)
1. **Planning and Initial Setup**
  - **Task**: Finalize project plan, scope, and requirements.
  - **Estimated Time**: 2 hours

2. **Learning Tailwind CSS**
  - **Task**: Begin learning Tailwind CSS basics.
  - **Estimated Time**: 2 hours

3. **Learning SVG Basics**
  - **Task**: Begin learning SVG basics.
  - **Estimated Time**: 2 hours

4. **Setup Development Environment**
  - **Task**: Configure project setup and tools.
  - **Estimated Time**: 2 hours

5. **Initial Database Design and Setup**
  - **Task**: Design database schema and create initial tables.
  - **Estimated Time**: 2 hours
  - **Testing Time**: 0.5 hours

   **Total for August 12th**: 8.5 hours

## August 13th (Tuesday)
6. **Design Database Schema for Additional Tables**
  - **Task**: Define schema requirements and create remaining tables.
  - **Estimated Time**: 2.5 hours
  - **Testing Time**: 0.5 hours

7. **Implement UserRepository with CRUD Operations**
  - **Task**: Define interface, implement repository, add methods.
  - **Estimated Time**: 3 hours
  - **Testing Time**: 1 hour

8. **Implement ModificationPlanRepository with CRUD Operations**
  - **Task**: Define interface, implement repository, add methods.
  - **Estimated Time**: 3 hours
  - **Testing Time**: 1 hour

   **Total for August 13th**: 10 hours

## August 14th (Wednesday)
9. **Implement PartRepository with CRUD Operations**
  - **Task**: Define interface, implement repository, add methods.
  - **Estimated Time**: 3 hours
  - **Testing Time**: 1 hour

10. **Implement SupplierRepository with CRUD Operations**
  - **Task**: Define interface, implement repository, add methods.
  - **Estimated Time**: 3 hours
  - **Testing Time**: 1 hour

11. **Implement UserService with Registration and Login Logic**
  - **Task**: Define service interface, implement registration and login.
  - **Estimated Time**: 3 hours
  - **Testing Time**: 1 hour

**Total for August 14th**: 10 hours

## August 15th (Thursday)
12. **Implement ModificationPlanService with CRUD Operations**
  - **Task**: Define service interface, implement CRUD operations.
  - **Estimated Time**: 3 hours
  - **Testing Time**: 1 hour

13. **Implement PartService with CRUD Operations**
  - **Task**: Define service interface, implement CRUD operations.
  - **Estimated Time**: 3 hours
  - **Testing Time**: 1 hour

14. **Implement SupplierService with CRUD Operations**
  - **Task**: Define service interface, implement CRUD operations.
  - **Estimated Time**: 3 hours
  - **Testing Time**: 1 hour

15. **Implement Security for User Login**
  - **Task**: Set up security configurations, implement login security.
  - **Estimated Time**: 2 hours

**Total for August 15th**: 10 hours

## August 16th (Friday)
16. **Implement UserController with Registration and Login Endpoints**
  - **Task**: Define REST endpoints, implement controller methods.
  - **Estimated Time**: 3 hours
  - **Testing Time**: 1 hour

17. **Implement ModificationPlanController with CRUD Endpoints**
  - **Task**: Define REST endpoints, implement controller methods.
  - **Estimated Time**: 3 hours
  - **Testing Time**: 1 hour

18. **Implement PartController with CRUD Endpoints**
  - **Task**: Define REST endpoints, implement controller methods.
  - **Estimated Time**: 3 hours
  - **Testing Time**: 1 hour

19. **Implement SupplierController with CRUD Endpoints**
  - **Task**: Define REST endpoints, implement controller methods.
  - **Estimated Time**: 3 hours
  - **Testing Time**: 1 hour

**Total for August 16th**: 12 hours

## August 17th (Saturday)
20. **Create PostForm Component with Tailwind CSS and SVG**
  - **Task**: Design, style, and integrate SVG.
  - **Estimated Time**: 4 hours
  - **Testing Time**: 1 hour

21. **Create CommentForm Component with Tailwind CSS and SVG**
  - **Task**: Design, style, and integrate SVG.
  - **Estimated Time**: 4 hours
  - **Testing Time**: 1 hour

22. **Implement Initial Frontend Integration**
  - **Task**: Integrate frontend components with backend services.
  - **Estimated Time**: 2 hours
  - **Testing Time**: 1 hour

**Total for August 17th**: 13 hours

## August 18th (Sunday)
23. **Review and Refactor Code**
  - **Task**: Review all implementations, refactor code if necessary.
  - **Estimated Time**: 3 hours

24. **Final Integration Testing**
  - **Task**: Conduct final integration tests for all components and endpoints.
  - **Estimated Time**: 3 hours

25. **Prepare Documentation**
  - **Task**: Document code, prepare project documentation.
  - **Estimated Time**: 2 hours

**Total for August 18th**: 8 hours

## August 19th (Monday)
26. **Deploy Application**
  - **Task**: Deploy application to production environment.
  - **Estimated Time**: 3 hours

27. **Final Review and Adjustments**
  - **Task**: Address any remaining issues, finalize project.
  - **Estimated Time**: 2 hours

28. **Buffer Time for Unforeseen Issues**
  - **Task**: Address any unexpected issues or last-minute changes.
  - **Estimated Time**: 3 hours

**Total for August 19th**: 8 hours

## August 20th (Tuesday)
29. **Stretch Goals Implementation (Optional)**
  - **Task**: Implement additional features like comments and posts if time permits.
  - **Estimated Time**: 4 hours

30. **Additional Testing and QA**
  - **Task**: Perform additional testing and quality assurance.
  - **Estimated Time**: 3 hours

**Total for August 20th**: 7 hours (Optional)

## August 21st (Wednesday)
31. **Project Wrap-Up and Final Checks**
  - **Task**: Complete any remaining tasks, finalize project.
  - **Estimated Time**: 3 hours

**Total for August 21st**: 3 hours

---

**Total Development Time**: 75 hours  
**Learning Time**: 6 hours  
**Testing Time**: 24 hours  
**Security Implementation Time**: 2 hours  
**Stretch Goals**: 22 hours

**Total Time Available**: 85 hours (excluding stretch goals)