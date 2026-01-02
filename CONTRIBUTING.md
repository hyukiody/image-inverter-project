# Contributing to Image Inverter Project

Thank you for your interest in contributing to the Image Inverter Project! We welcome contributions from the community.

## Table of Contents

- [Code of Conduct](#code-of-conduct)
- [How Can I Contribute?](#how-can-i-contribute)
- [Getting Started](#getting-started)
- [Development Workflow](#development-workflow)
- [Coding Standards](#coding-standards)
- [Submitting Changes](#submitting-changes)
- [Reporting Bugs](#reporting-bugs)
- [Suggesting Enhancements](#suggesting-enhancements)

## Code of Conduct

This project and everyone participating in it is governed by our [Code of Conduct](CODE_OF_CONDUCT.md). By participating, you are expected to uphold this code.

## How Can I Contribute?

### Reporting Bugs

Before creating bug reports, please check the existing issues to avoid duplicates. When you create a bug report, include as many details as possible:

- **Use a clear and descriptive title**
- **Describe the exact steps to reproduce the problem**
- **Provide specific examples to demonstrate the steps**
- **Describe the behavior you observed and what you expected**
- **Include screenshots if relevant**
- **Include your environment details** (Java version, OS, etc.)

### Suggesting Enhancements

Enhancement suggestions are tracked as GitHub issues. When creating an enhancement suggestion:

- **Use a clear and descriptive title**
- **Provide a detailed description of the suggested enhancement**
- **Explain why this enhancement would be useful**
- **List any relevant examples from other projects**

### Pull Requests

We actively welcome your pull requests:

1. Fork the repo and create your branch from `main`
2. Make your changes following our coding standards
3. Add tests if you've added functionality
4. Ensure the test suite passes
5. Update the documentation if needed
6. Submit your pull request!

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- Git

### Setting Up Development Environment

1. **Fork the repository** on GitHub

2. **Clone your fork** locally:
   ```bash
   git clone https://github.com/YOUR_USERNAME/image-inverter-project.git
   cd image-inverter-project
   ```

3. **Add the upstream repository**:
   ```bash
   git remote add upstream https://github.com/hyukiody/image-inverter-project.git
   ```

4. **Build the project**:
   ```bash
   mvn clean install
   ```

5. **Run the tests**:
   ```bash
   mvn test
   ```

## Development Workflow

### Creating a Branch

Create a new branch for your work:

```bash
git checkout -b feature/your-feature-name
```

Branch naming conventions:
- `feature/` - New features
- `bugfix/` - Bug fixes
- `docs/` - Documentation updates
- `refactor/` - Code refactoring
- `test/` - Test additions or updates

### Making Changes

1. Make your changes in your feature branch
2. Write or update tests as needed
3. Ensure all tests pass: `mvn test`
4. Build the project: `mvn clean package`
5. Test the application manually if needed: `mvn spring-boot:run`

### Keeping Your Fork Updated

Regularly sync your fork with the upstream repository:

```bash
git fetch upstream
git checkout main
git merge upstream/main
git push origin main
```

## Coding Standards

### Java Code Style

- Follow standard Java naming conventions
- Use meaningful variable and method names
- Write clear, concise comments for complex logic
- Keep methods focused and not too long
- Use Spring Boot best practices
- Follow SOLID principles

### Code Formatting

- Use 4 spaces for indentation (not tabs)
- Maximum line length: 120 characters
- Use braces even for single-line if statements
- Place opening braces on the same line

### Documentation

- Document all public APIs with JavaDoc
- Update README.md if you change functionality
- Add inline comments for complex algorithms
- Keep documentation up-to-date with code changes

### Testing

- Write unit tests for new functionality
- Aim for good test coverage
- Use descriptive test method names
- Follow the Arrange-Act-Assert pattern
- Mock external dependencies

Example test structure:
```java
@Test
void shouldInvertImageSuccessfully() {
    // Arrange
    byte[] inputImage = createTestImage();
    
    // Act
    byte[] result = imageService.invertImage(inputImage);
    
    // Assert
    assertNotNull(result);
    assertImageIsInverted(result);
}
```

## Submitting Changes

### Before Submitting

1. **Run all tests**: `mvn test`
2. **Build the project**: `mvn clean package`
3. **Verify code quality**: Check for warnings
4. **Update documentation**: Ensure docs reflect your changes
5. **Commit your changes**: Use clear commit messages

### Commit Message Guidelines

Write clear, descriptive commit messages:

```
Short summary (50 chars or less)

More detailed explanation if needed. Wrap at 72 characters.
Explain what and why, not how.

- Bullet points are okay
- Use imperative mood ("Add feature" not "Added feature")
```

Examples:
- ✅ `Add JSON response format for image inversion`
- ✅ `Fix memory leak in image processing`
- ✅ `Update API documentation for new endpoints`
- ❌ `Fixed stuff`
- ❌ `WIP`

### Creating a Pull Request

1. **Push your changes** to your fork:
   ```bash
   git push origin feature/your-feature-name
   ```

2. **Create a Pull Request** on GitHub from your branch to `main`

3. **Fill in the PR template** with:
   - Description of changes
   - Related issue numbers (if applicable)
   - Testing performed
   - Screenshots (if UI changes)

4. **Wait for review** - maintainers will review your PR

5. **Address feedback** - make changes if requested

6. **Merge** - once approved, your PR will be merged!

### PR Review Process

- At least one maintainer approval is required
- All tests must pass
- Code should follow our standards
- Documentation should be updated
- No merge conflicts

## Reporting Bugs

### Before Submitting a Bug Report

- Check the documentation
- Search existing issues
- Try the latest version
- Collect relevant information

### How to Submit a Bug Report

Use the issue tracker and include:

- **Title**: Clear, specific summary
- **Environment**: Java version, OS, Maven version
- **Steps to reproduce**: Detailed steps
- **Expected behavior**: What should happen
- **Actual behavior**: What actually happens
- **Logs/Screenshots**: Any relevant output
- **Possible fix**: If you have ideas

## Suggesting Enhancements

We love new ideas! When suggesting enhancements:

1. **Check existing issues** to avoid duplicates
2. **Provide a clear use case** for the enhancement
3. **Describe the proposed solution** in detail
4. **Consider alternatives** you've thought about
5. **Explain why this would be useful** to most users

## Questions?

Feel free to open an issue with your question or reach out to the maintainers.

## License

By contributing, you agree that your contributions will be licensed under the same license as the project (MIT License).

---

Thank you for contributing to the Image Inverter Project! 🎉
