console.log(location.port);
const port = location.port === "8081" ? "8080" : location.port;
export const apiUrl =
  location.protocol +
  "//" +
  location.hostname +
  ":" +
  port +
  process.env.BASE_URL +
  "api/";
console.log(apiUrl);
