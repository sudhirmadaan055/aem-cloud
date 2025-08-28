// mcp.config.js

import dotenv from 'dotenv';
dotenv.config(); // Load environment variables

export default {
  figma: {
    apiKey: process.env.FIGMA_API_KEY,  // Figma API Key from .env
    fileKey: process.env.FIGMA_FILE_KEY, // Figma File Key from .env
  },
  mcpServerUrl: 'http://localhost:3333',  // The URL where your MCP server is running (default)
};
