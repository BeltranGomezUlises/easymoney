var config = {
   entry: './main.js',

   output: {
      path:__dirname + '/builds',
      filename: 'index.js'
   },

   devServer: {
      inline: true,
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
