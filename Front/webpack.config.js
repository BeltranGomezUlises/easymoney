var config = {
   entry: './main.js',

   output: {
      path:__dirname + '/',
      filename: 'index.js'
   },

   devServer: {
      inline: true,
      port: 8888
   },

   module: {
      rules: [
         {
            test: /\.jsx?$/,
            exclude: /node_modules/,
            loader: 'babel-loader',

            query: {
               presets: ['es2015', 'react']
            }
         }
      ]
   }
}

module.exports = config;
