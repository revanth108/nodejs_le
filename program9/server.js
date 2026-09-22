const http = require("http");
const os = require("os");
const path = require("path");
const EventEmitter = require("events");

// Create a custom event emitter
const eventEmitter = new EventEmitter();
// Create an HTTP server
const server = http.createServer((req, res) => {
    if (req.url === "/") {
res.writeHead(200, { "Content-Type": "text/plain" });
res.end("Welcome to the Node.js Server!");
    } else if (req.url === "/os") {
res.writeHead(200, { "Content-Type": "text/plain" });
res.end(`OS Info: ${os.type()} ${os.release()}`);
    } else if (req.url === "/path") {
        const filePath = path.join(__dirname, "server.js");
res.writeHead(200, { "Content-Type": "text/plain" });
res.end(`File Path: ${filePath}`);
    } else if (req.url === "/event") {
eventEmitter.on("greet", () => {
res.writeHead(200, { "Content-Type": "text/plain" });
res.end("Event Emitted: Hello World!");
        });
eventEmitter.emit("greet");
    } else {
res.writeHead(404, { "Content-Type": "text/plain" });
res.end("404 Not Found");
    }
});
// Start the server
const PORT = 3000;
server.listen(PORT, () => {
console.log(`Server is running on http://localhost:${PORT}`);
});
