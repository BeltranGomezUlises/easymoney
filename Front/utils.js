import {notify} from 'react-notify-toast';

/*
* callback a ejecutar si la respuesta del servicio en su status es OK
* en caso contrario, noticia al usuario con advertencia o error
*/
export function evalResponse(response, callback){
  switch(response.meta.status){
    case 'OK':
      if (callback){
        callback();
      }
      break;
    case 'WARNING':
      notify.show(response.meta.message, 'warning', 6000);
      break;
    case 'ERROR':
      notify.show(response.meta.message + ' causado por: ' + response.meta.devMessage, 'error', 8000);
      break;
  }
}

/*
* devuelve el valor en utc de la fecha local en long de su representacion en cadena
* ejemplo: de "12/12/2017" con GMT -7, regresa su valor en utc equivalente
*/
export function toUtcDate(dateString){
  let date = new Date(dateString);
  return date.getTime() + (date.getTimezoneOffset() * 60000);
}
