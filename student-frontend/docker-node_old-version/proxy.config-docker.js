const proxy = [
    {
      context: '/api',
      target: 'http://student-backend:8081/student-backend/',
      pathRewrite: {'^/api' : ''}
    }
  ];
  
  module.exports = proxy;
  
