import {notify} from 'react-notify-toast';

/*
* callback a ejecutar si la respuesta del servicio en su status es OK
* en caso contrario, noticia al usuario con advertencia o error
*/
export function evalResponse(response, callback){
  switch(response.meta.status){
    case 'OK':
      callback();
      break;
    case 'WARNING':
      notify.show(response.meta.message, 'warning', 6000);
      console.log(response);
      break;
    case 'ERROR':
      notify.show(response.meta.message + ' causado por: ' + response.meta.devMessage, 'error', 8000);
      console.log(response);
      break;
  }
}
