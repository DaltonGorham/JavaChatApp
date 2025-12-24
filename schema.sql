-- Java Chat Application Database Schema
-- Database: chatappdb

-- Create the database (run as postgres superuser)
-- CREATE DATABASE chatappdb;

-- Connect to the database
-- \c chatappdb

-- Table for general chat messages
CREATE TABLE IF NOT EXISTS messages (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table for direct messages between users
CREATE TABLE IF NOT EXISTS direct_messages (
    id SERIAL PRIMARY KEY,
    sender VARCHAR(255) NOT NULL,
    recipient VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes for better query performance
CREATE INDEX IF NOT EXISTS idx_messages_timestamp ON messages(timestamp);
CREATE INDEX IF NOT EXISTS idx_direct_messages_users ON direct_messages(sender, recipient);
CREATE INDEX IF NOT EXISTS idx_direct_messages_timestamp ON direct_messages(timestamp);

-- Optional: Create a view for recent messages
CREATE OR REPLACE VIEW recent_messages AS
SELECT username, message, timestamp
FROM messages
ORDER BY timestamp DESC
LIMIT 100;

-- Optional: Create a view for recent direct messages
CREATE OR REPLACE VIEW recent_direct_messages AS
SELECT sender, recipient, message, timestamp
FROM direct_messages
ORDER BY timestamp DESC
LIMIT 100;
