window.onload = function() {
  // Configuration Swagger UI
  window.ui = SwaggerUIBundle({
    url: "/M2iVinyleMaven/api/openapi", // <- ton endpoint OpenApiResource
    dom_id: '#swagger-ui',
    deepLinking: true,
    presets: [
      SwaggerUIBundle.presets.apis,
      SwaggerUIStandalonePreset
    ],
    plugins: [
      SwaggerUIBundle.plugins.DownloadUrl
    ],
    layout: "StandaloneLayout"
  });
};
