#!/bin/bash

echo "=== Starting Keycloak configuration ==="

# Wait for Keycloak to be ready
echo "Waiting for Keycloak to be ready..."
for i in {1..60}; do
  if curl -s -f http://keycloak:8080/health/ready > /dev/null 2>&1; then
    echo "✓ Keycloak is ready!"
    break
  fi
  if [ $i -eq 60 ]; then
    echo "✗ Timeout waiting for Keycloak"
    exit 1
  fi
  echo "  Attempt $i/60..."
  sleep 2
done

echo "Keycloak setup completed successfully!"
echo "Admin available at: http://localhost:8180"
echo "Credentials: admin/admin"
