export default function(vue: any) {
    // if (process.client) {
      const protocol = window.location.protocol
      const hostname = window.location.hostname
      const port = window.location.port
      const url = `${protocol}//${hostname}:${port}`
      console.log("setting base url " + url);
      // vue.$axios.defaults.baseURL = url
      vue.$axios.defaults.baseURL = "http://localhost:8080"
    // }
  }