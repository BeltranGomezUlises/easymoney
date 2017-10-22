#include <enunciate-common.c>
#ifndef DEF_EasyMoney_ns0_modelLogin_H
#define DEF_EasyMoney_ns0_modelLogin_H

/**
 * 
 *  @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
struct EasyMoney_ns0_modelLogin {


  /**
   * (no documentation provided)
   */
  xmlChar *user;

  /**
   * (no documentation provided)
   */
  xmlChar *pass;
};

/**
 * Reads a ModelLogin from XML. The reader is assumed to be at the start element.
 *
 * @param reader The XML reader.
 * @return The ModelLogin, or NULL in case of error.
 */
static struct EasyMoney_ns0_modelLogin *xmlTextReaderReadNs0ModelLoginType(xmlTextReaderPtr reader);

/**
 * Writes a ModelLogin to XML.
 *
 * @param writer The XML writer.
 * @param _modelLogin The ModelLogin to write.
 * @return The bytes written (may be 0 in case of buffering) or -1 in case of error.
 */
static int xmlTextWriterWriteNs0ModelLoginType(xmlTextWriterPtr writer, struct EasyMoney_ns0_modelLogin *_modelLogin);

/**
 * Frees the elements of a ModelLogin.
 *
 * @param _modelLogin The ModelLogin to free.
 */
static void freeNs0ModelLoginType(struct EasyMoney_ns0_modelLogin *_modelLogin);

#endif /* DEF_EasyMoney_ns0_modelLogin_H */
#ifndef DEF_EasyMoney_ns0_status_H
#define DEF_EasyMoney_ns0_status_H

/**
 *  enumerador de estado de operacion
 *  @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
enum EasyMoney_ns0_status {

  /**
   * (no documentation provided)
   */
  EASYMONEY_NS0_STATUS_OK,

  /**
   * (no documentation provided)
   */
  EASYMONEY_NS0_STATUS_ERROR,

  /**
   * (no documentation provided)
   */
  EASYMONEY_NS0_STATUS_WARNING,

  /**
   * (no documentation provided)
   */
  EASYMONEY_NS0_STATUS_ACCES_DENIED,

  /**
   * (no documentation provided)
   */
  EASYMONEY_NS0_STATUS_INVALID_PARAM,

  /**
   * (no documentation provided)
   */
  EASYMONEY_NS0_STATUS_PARCIAL_ACCESS
};

/**
 * Reads a Status from XML. The reader is assumed to be at the start element.
 *
 * @param reader The XML reader.
 * @return The Status, or NULL if unable to be read.
 */
static enum EasyMoney_ns0_status *xmlTextReaderReadNs0StatusType(xmlTextReaderPtr reader);

/**
 * Writes a Status to XML.
 *
 * @param writer The XML writer.
 * @param _status The Status to write.
 * @return The bytes written (may be 0 in case of buffering) or -1 in case of error.
 */
static int xmlTextWriterWriteNs0StatusType(xmlTextWriterPtr writer, enum EasyMoney_ns0_status *_status);

/**
 * Frees a Status.
 *
 * @param _status The Status to free.
 */
static void freeNs0StatusType(enum EasyMoney_ns0_status *_status);

#endif
#ifndef DEF_EasyMoney_ns0_metaData_H
#define DEF_EasyMoney_ns0_metaData_H

/**
 *  modelo contenedor de los metadatos de una respuesta
 *  @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
struct EasyMoney_ns0_metaData {


  /**
   * (no documentation provided)
   */
  xmlChar *message;

  /**
   * (no documentation provided)
   */
  enum EasyMoney_ns0_status *status;

  /**
   * (no documentation provided)
   */
  xmlChar *devMessage;

  /**
   * (no documentation provided)
   */
  struct xmlBasicNode *metaData;
};

/**
 * Reads a MetaData from XML. The reader is assumed to be at the start element.
 *
 * @param reader The XML reader.
 * @return The MetaData, or NULL in case of error.
 */
static struct EasyMoney_ns0_metaData *xmlTextReaderReadNs0MetaDataType(xmlTextReaderPtr reader);

/**
 * Writes a MetaData to XML.
 *
 * @param writer The XML writer.
 * @param _metaData The MetaData to write.
 * @return The bytes written (may be 0 in case of buffering) or -1 in case of error.
 */
static int xmlTextWriterWriteNs0MetaDataType(xmlTextWriterPtr writer, struct EasyMoney_ns0_metaData *_metaData);

/**
 * Frees the elements of a MetaData.
 *
 * @param _metaData The MetaData to free.
 */
static void freeNs0MetaDataType(struct EasyMoney_ns0_metaData *_metaData);

#endif /* DEF_EasyMoney_ns0_metaData_H */
#ifndef DEF_EasyMoney_ns0_response_H
#define DEF_EasyMoney_ns0_response_H

/**
 *  modelo de respuesta generico para servicios
 *  @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
struct EasyMoney_ns0_response {


  /**
   * (no documentation provided)
   */
  struct xmlBasicNode *data;

  /**
   * (no documentation provided)
   */
  struct EasyMoney_ns0_metaData *meta;
};

/**
 * Reads a Response from XML. The reader is assumed to be at the start element.
 *
 * @param reader The XML reader.
 * @return The Response, or NULL in case of error.
 */
static struct EasyMoney_ns0_response *xmlTextReaderReadNs0ResponseType(xmlTextReaderPtr reader);

/**
 * Writes a Response to XML.
 *
 * @param writer The XML writer.
 * @param _response The Response to write.
 * @return The bytes written (may be 0 in case of buffering) or -1 in case of error.
 */
static int xmlTextWriterWriteNs0ResponseType(xmlTextWriterPtr writer, struct EasyMoney_ns0_response *_response);

/**
 * Frees the elements of a Response.
 *
 * @param _response The Response to free.
 */
static void freeNs0ResponseType(struct EasyMoney_ns0_response *_response);

#endif /* DEF_EasyMoney_ns0_response_H */
#ifndef DEF_EasyMoney_ns0_modelLogin_M
#define DEF_EasyMoney_ns0_modelLogin_M

/**
 * Reads a ModelLogin from XML. The reader is assumed to be at the start element.
 *
 * @return the ModelLogin, or NULL in case of error.
 */
static struct EasyMoney_ns0_modelLogin *xmlTextReaderReadNs0ModelLoginType(xmlTextReaderPtr reader) {
  int status, depth;
  void *_child_accessor;
  struct EasyMoney_ns0_modelLogin *_modelLogin = calloc(1, sizeof(struct EasyMoney_ns0_modelLogin));



  if (xmlTextReaderIsEmptyElement(reader) == 0) {
    depth = xmlTextReaderDepth(reader);//track the depth.
    status = xmlTextReaderAdvanceToNextStartOrEndElement(reader);

    while (xmlTextReaderDepth(reader) > depth) {
      if (status < 1) {
        //panic: XML read error.
#if DEBUG_ENUNCIATE
        printf("Failure to advance to next child element.\n");
#endif
        freeNs0ModelLoginType(_modelLogin);
        free(_modelLogin);
        return NULL;
      }
      else if (xmlTextReaderNodeType(reader) == XML_READER_TYPE_ELEMENT
        && xmlStrcmp(BAD_CAST "user", xmlTextReaderConstLocalName(reader)) == 0
        && xmlTextReaderConstNamespaceUri(reader) == NULL) {

#if DEBUG_ENUNCIATE > 1
        printf("Attempting to read choice {}user of type {http://www.w3.org/2001/XMLSchema}string.\n");
#endif
        _child_accessor = xmlTextReaderReadXsStringType(reader);
        if (_child_accessor == NULL) {
#if DEBUG_ENUNCIATE
          printf("Failed to read choice {}user of type {http://www.w3.org/2001/XMLSchema}string.\n");
#endif
          //panic: unable to read the child element for some reason.
          freeNs0ModelLoginType(_modelLogin);
          free(_modelLogin);
          return NULL;
        }

        _modelLogin->user = ((xmlChar*)_child_accessor);
        status = xmlTextReaderAdvanceToNextStartOrEndElement(reader);
      }
      else if (xmlTextReaderNodeType(reader) == XML_READER_TYPE_ELEMENT
        && xmlStrcmp(BAD_CAST "pass", xmlTextReaderConstLocalName(reader)) == 0
        && xmlTextReaderConstNamespaceUri(reader) == NULL) {

#if DEBUG_ENUNCIATE > 1
        printf("Attempting to read choice {}pass of type {http://www.w3.org/2001/XMLSchema}string.\n");
#endif
        _child_accessor = xmlTextReaderReadXsStringType(reader);
        if (_child_accessor == NULL) {
#if DEBUG_ENUNCIATE
          printf("Failed to read choice {}pass of type {http://www.w3.org/2001/XMLSchema}string.\n");
#endif
          //panic: unable to read the child element for some reason.
          freeNs0ModelLoginType(_modelLogin);
          free(_modelLogin);
          return NULL;
        }

        _modelLogin->pass = ((xmlChar*)_child_accessor);
        status = xmlTextReaderAdvanceToNextStartOrEndElement(reader);
      }
      else {
#if DEBUG_ENUNCIATE > 1
        if (xmlTextReaderConstNamespaceUri(reader) == NULL) {
          printf("unknown child element {}%s for type {}modelLogin.  Skipping...\n",  xmlTextReaderConstLocalName(reader));
        }
        else {
          printf("unknown child element {%s}%s for type {}modelLogin. Skipping...\n", xmlTextReaderConstNamespaceUri(reader), xmlTextReaderConstLocalName(reader));
        }
#endif
        status = xmlTextReaderSkipElement(reader);
      }
    }
  }

  return _modelLogin;
}

/**
 * Writes a ModelLogin to XML.
 *
 * @param writer The XML writer.
 * @param _modelLogin The ModelLogin to write.
 * @return The total bytes written, or -1 on error;
 */
static int xmlTextWriterWriteNs0ModelLoginType(xmlTextWriterPtr writer, struct EasyMoney_ns0_modelLogin *_modelLogin) {
  int status, totalBytes = 0, i;
  xmlChar *binaryData;
  if (_modelLogin->user != NULL) {
    status = xmlTextWriterStartElementNS(writer, NULL, BAD_CAST "user", NULL);
    if (status < 0) {
#if DEBUG_ENUNCIATE
      printf("Failed to write start element {}user. status: %i\n", status);
#endif
      return status;
    }
    totalBytes += status;
#if DEBUG_ENUNCIATE > 1
    printf("writing type {http://www.w3.org/2001/XMLSchema}string for element {}user...\n");
#endif
    status = xmlTextWriterWriteXsStringType(writer, (_modelLogin->user));
    if (status < 0) {
#if DEBUG_ENUNCIATE
      printf("Failed to write type {http://www.w3.org/2001/XMLSchema}string for element {}user. status: %i\n", status);
#endif
      return status;
    }
    totalBytes += status;

    status = xmlTextWriterEndElement(writer);
    if (status < 0) {
#if DEBUG_ENUNCIATE
      printf("Failed to write end element {}user. status: %i\n", status);
#endif
      return status;
    }
    totalBytes += status;
  }
  if (_modelLogin->pass != NULL) {
    status = xmlTextWriterStartElementNS(writer, NULL, BAD_CAST "pass", NULL);
    if (status < 0) {
#if DEBUG_ENUNCIATE
      printf("Failed to write start element {}pass. status: %i\n", status);
#endif
      return status;
    }
    totalBytes += status;
#if DEBUG_ENUNCIATE > 1
    printf("writing type {http://www.w3.org/2001/XMLSchema}string for element {}pass...\n");
#endif
    status = xmlTextWriterWriteXsStringType(writer, (_modelLogin->pass));
    if (status < 0) {
#if DEBUG_ENUNCIATE
      printf("Failed to write type {http://www.w3.org/2001/XMLSchema}string for element {}pass. status: %i\n", status);
#endif
      return status;
    }
    totalBytes += status;

    status = xmlTextWriterEndElement(writer);
    if (status < 0) {
#if DEBUG_ENUNCIATE
      printf("Failed to write end element {}pass. status: %i\n", status);
#endif
      return status;
    }
    totalBytes += status;
  }

  return totalBytes;
}

/**
 * Frees the elements of a ModelLogin.
 *
 * @param _modelLogin The ModelLogin to free.
 */
static void freeNs0ModelLoginType(struct EasyMoney_ns0_modelLogin *_modelLogin) {
  int i;
  if (_modelLogin->user != NULL) {
#if DEBUG_ENUNCIATE > 1
    printf("Freeing type of accessor user of type EasyMoney_ns0_modelLogin...\n");
#endif
    freeXsStringType(_modelLogin->user);
#if DEBUG_ENUNCIATE > 1
    printf("Freeing accessor user of type EasyMoney_ns0_modelLogin...\n");
#endif
    free(_modelLogin->user);
  }
  if (_modelLogin->pass != NULL) {
#if DEBUG_ENUNCIATE > 1
    printf("Freeing type of accessor pass of type EasyMoney_ns0_modelLogin...\n");
#endif
    freeXsStringType(_modelLogin->pass);
#if DEBUG_ENUNCIATE > 1
    printf("Freeing accessor pass of type EasyMoney_ns0_modelLogin...\n");
#endif
    free(_modelLogin->pass);
  }
}
#endif /* DEF_EasyMoney_ns0_modelLogin_M */
#ifndef DEF_EasyMoney_ns0_status_M
#define DEF_EasyMoney_ns0_status_M

/**
 * Reads a Status from XML. The reader is assumed to be at the start element.
 *
 * @param reader The XML reader.
 * @return The Status, or NULL if unable to be read.
 */
static enum EasyMoney_ns0_status *xmlTextReaderReadNs0StatusType(xmlTextReaderPtr reader) {
  xmlChar *enumValue = xmlTextReaderReadEntireNodeValue(reader);
  enum EasyMoney_ns0_status *value = calloc(1, sizeof(enum EasyMoney_ns0_status));
  if (enumValue != NULL) {
    if (xmlStrcmp(enumValue, BAD_CAST "OK") == 0) {
      *value = EASYMONEY_NS0_STATUS_OK;
      free(enumValue);
      return value;
    }
    if (xmlStrcmp(enumValue, BAD_CAST "ERROR") == 0) {
      *value = EASYMONEY_NS0_STATUS_ERROR;
      free(enumValue);
      return value;
    }
    if (xmlStrcmp(enumValue, BAD_CAST "WARNING") == 0) {
      *value = EASYMONEY_NS0_STATUS_WARNING;
      free(enumValue);
      return value;
    }
    if (xmlStrcmp(enumValue, BAD_CAST "ACCES_DENIED") == 0) {
      *value = EASYMONEY_NS0_STATUS_ACCES_DENIED;
      free(enumValue);
      return value;
    }
    if (xmlStrcmp(enumValue, BAD_CAST "INVALID_PARAM") == 0) {
      *value = EASYMONEY_NS0_STATUS_INVALID_PARAM;
      free(enumValue);
      return value;
    }
    if (xmlStrcmp(enumValue, BAD_CAST "PARCIAL_ACCESS") == 0) {
      *value = EASYMONEY_NS0_STATUS_PARCIAL_ACCESS;
      free(enumValue);
      return value;
    }
#if DEBUG_ENUNCIATE
    printf("Attempt to read enum value failed: %s doesn't match an enum value.\n", enumValue);
#endif
  }
#if DEBUG_ENUNCIATE
  else {
    printf("Attempt to read enum value failed: NULL value.\n");
  }
#endif

  return NULL;
}

/**
 * Writes a Status to XML.
 *
 * @param writer The XML writer.
 * @param _status The Status to write.
 * @return The bytes written (may be 0 in case of buffering) or -1 in case of error.
 */
static int xmlTextWriterWriteNs0StatusType(xmlTextWriterPtr writer, enum EasyMoney_ns0_status *_status) {
  switch (*_status) {
    case EASYMONEY_NS0_STATUS_OK:
      return xmlTextWriterWriteString(writer, BAD_CAST "OK");
    case EASYMONEY_NS0_STATUS_ERROR:
      return xmlTextWriterWriteString(writer, BAD_CAST "ERROR");
    case EASYMONEY_NS0_STATUS_WARNING:
      return xmlTextWriterWriteString(writer, BAD_CAST "WARNING");
    case EASYMONEY_NS0_STATUS_ACCES_DENIED:
      return xmlTextWriterWriteString(writer, BAD_CAST "ACCES_DENIED");
    case EASYMONEY_NS0_STATUS_INVALID_PARAM:
      return xmlTextWriterWriteString(writer, BAD_CAST "INVALID_PARAM");
    case EASYMONEY_NS0_STATUS_PARCIAL_ACCESS:
      return xmlTextWriterWriteString(writer, BAD_CAST "PARCIAL_ACCESS");
  }

#if DEBUG_ENUNCIATE
  printf("Unable to write enum value (no valid value found).\n");
#endif
  return -1;
}

/**
 * Frees a Status.
 *
 * @param _status The Status to free.
 */
static void freeNs0StatusType(enum EasyMoney_ns0_status *_status) {
  //no-op
}
#endif /* DEF_EasyMoney_ns0_status_M */
#ifndef DEF_EasyMoney_ns0_metaData_M
#define DEF_EasyMoney_ns0_metaData_M

/**
 * Reads a MetaData from XML. The reader is assumed to be at the start element.
 *
 * @return the MetaData, or NULL in case of error.
 */
static struct EasyMoney_ns0_metaData *xmlTextReaderReadNs0MetaDataType(xmlTextReaderPtr reader) {
  int status, depth;
  void *_child_accessor;
  struct EasyMoney_ns0_metaData *_metaData = calloc(1, sizeof(struct EasyMoney_ns0_metaData));



  if (xmlTextReaderIsEmptyElement(reader) == 0) {
    depth = xmlTextReaderDepth(reader);//track the depth.
    status = xmlTextReaderAdvanceToNextStartOrEndElement(reader);

    while (xmlTextReaderDepth(reader) > depth) {
      if (status < 1) {
        //panic: XML read error.
#if DEBUG_ENUNCIATE
        printf("Failure to advance to next child element.\n");
#endif
        freeNs0MetaDataType(_metaData);
        free(_metaData);
        return NULL;
      }
      else if (xmlTextReaderNodeType(reader) == XML_READER_TYPE_ELEMENT
        && xmlStrcmp(BAD_CAST "message", xmlTextReaderConstLocalName(reader)) == 0
        && xmlTextReaderConstNamespaceUri(reader) == NULL) {

#if DEBUG_ENUNCIATE > 1
        printf("Attempting to read choice {}message of type {http://www.w3.org/2001/XMLSchema}string.\n");
#endif
        _child_accessor = xmlTextReaderReadXsStringType(reader);
        if (_child_accessor == NULL) {
#if DEBUG_ENUNCIATE
          printf("Failed to read choice {}message of type {http://www.w3.org/2001/XMLSchema}string.\n");
#endif
          //panic: unable to read the child element for some reason.
          freeNs0MetaDataType(_metaData);
          free(_metaData);
          return NULL;
        }

        _metaData->message = ((xmlChar*)_child_accessor);
        status = xmlTextReaderAdvanceToNextStartOrEndElement(reader);
      }
      else if (xmlTextReaderNodeType(reader) == XML_READER_TYPE_ELEMENT
        && xmlStrcmp(BAD_CAST "status", xmlTextReaderConstLocalName(reader)) == 0
        && xmlTextReaderConstNamespaceUri(reader) == NULL) {

#if DEBUG_ENUNCIATE > 1
        printf("Attempting to read choice {}status of type {}status.\n");
#endif
        _child_accessor = xmlTextReaderReadNs0StatusType(reader);
        if (_child_accessor == NULL) {
#if DEBUG_ENUNCIATE
          printf("Failed to read choice {}status of type {}status.\n");
#endif
          //panic: unable to read the child element for some reason.
          freeNs0MetaDataType(_metaData);
          free(_metaData);
          return NULL;
        }

        _metaData->status = ((enum EasyMoney_ns0_status*)_child_accessor);
        status = xmlTextReaderAdvanceToNextStartOrEndElement(reader);
      }
      else if (xmlTextReaderNodeType(reader) == XML_READER_TYPE_ELEMENT
        && xmlStrcmp(BAD_CAST "devMessage", xmlTextReaderConstLocalName(reader)) == 0
        && xmlTextReaderConstNamespaceUri(reader) == NULL) {

#if DEBUG_ENUNCIATE > 1
        printf("Attempting to read choice {}devMessage of type {http://www.w3.org/2001/XMLSchema}string.\n");
#endif
        _child_accessor = xmlTextReaderReadXsStringType(reader);
        if (_child_accessor == NULL) {
#if DEBUG_ENUNCIATE
          printf("Failed to read choice {}devMessage of type {http://www.w3.org/2001/XMLSchema}string.\n");
#endif
          //panic: unable to read the child element for some reason.
          freeNs0MetaDataType(_metaData);
          free(_metaData);
          return NULL;
        }

        _metaData->devMessage = ((xmlChar*)_child_accessor);
        status = xmlTextReaderAdvanceToNextStartOrEndElement(reader);
      }
      else if (xmlTextReaderNodeType(reader) == XML_READER_TYPE_ELEMENT
        && xmlStrcmp(BAD_CAST "metaData", xmlTextReaderConstLocalName(reader)) == 0
        && xmlTextReaderConstNamespaceUri(reader) == NULL) {

#if DEBUG_ENUNCIATE > 1
        printf("Attempting to read choice {}metaData of type {http://www.w3.org/2001/XMLSchema}anyType.\n");
#endif
        _child_accessor = xmlTextReaderReadXsAnyTypeType(reader);
        if (_child_accessor == NULL) {
#if DEBUG_ENUNCIATE
          printf("Failed to read choice {}metaData of type {http://www.w3.org/2001/XMLSchema}anyType.\n");
#endif
          //panic: unable to read the child element for some reason.
          freeNs0MetaDataType(_metaData);
          free(_metaData);
          return NULL;
        }

        _metaData->metaData = ((struct xmlBasicNode*)_child_accessor);
        status = xmlTextReaderAdvanceToNextStartOrEndElement(reader);
      }
      else {
#if DEBUG_ENUNCIATE > 1
        if (xmlTextReaderConstNamespaceUri(reader) == NULL) {
          printf("unknown child element {}%s for type {}metaData.  Skipping...\n",  xmlTextReaderConstLocalName(reader));
        }
        else {
          printf("unknown child element {%s}%s for type {}metaData. Skipping...\n", xmlTextReaderConstNamespaceUri(reader), xmlTextReaderConstLocalName(reader));
        }
#endif
        status = xmlTextReaderSkipElement(reader);
      }
    }
  }

  return _metaData;
}

/**
 * Writes a MetaData to XML.
 *
 * @param writer The XML writer.
 * @param _metaData The MetaData to write.
 * @return The total bytes written, or -1 on error;
 */
static int xmlTextWriterWriteNs0MetaDataType(xmlTextWriterPtr writer, struct EasyMoney_ns0_metaData *_metaData) {
  int status, totalBytes = 0, i;
  xmlChar *binaryData;
  if (_metaData->message != NULL) {
    status = xmlTextWriterStartElementNS(writer, NULL, BAD_CAST "message", NULL);
    if (status < 0) {
#if DEBUG_ENUNCIATE
      printf("Failed to write start element {}message. status: %i\n", status);
#endif
      return status;
    }
    totalBytes += status;
#if DEBUG_ENUNCIATE > 1
    printf("writing type {http://www.w3.org/2001/XMLSchema}string for element {}message...\n");
#endif
    status = xmlTextWriterWriteXsStringType(writer, (_metaData->message));
    if (status < 0) {
#if DEBUG_ENUNCIATE
      printf("Failed to write type {http://www.w3.org/2001/XMLSchema}string for element {}message. status: %i\n", status);
#endif
      return status;
    }
    totalBytes += status;

    status = xmlTextWriterEndElement(writer);
    if (status < 0) {
#if DEBUG_ENUNCIATE
      printf("Failed to write end element {}message. status: %i\n", status);
#endif
      return status;
    }
    totalBytes += status;
  }
  if (_metaData->status != NULL) {
    status = xmlTextWriterStartElementNS(writer, NULL, BAD_CAST "status", NULL);
    if (status < 0) {
#if DEBUG_ENUNCIATE
      printf("Failed to write start element {}status. status: %i\n", status);
#endif
      return status;
    }
    totalBytes += status;
#if DEBUG_ENUNCIATE > 1
    printf("writing type {}status for element {}status...\n");
#endif
    status = xmlTextWriterWriteNs0StatusType(writer, (_metaData->status));
    if (status < 0) {
#if DEBUG_ENUNCIATE
      printf("Failed to write type {}status for element {}status. status: %i\n", status);
#endif
      return status;
    }
    totalBytes += status;

    status = xmlTextWriterEndElement(writer);
    if (status < 0) {
#if DEBUG_ENUNCIATE
      printf("Failed to write end element {}status. status: %i\n", status);
#endif
      return status;
    }
    totalBytes += status;
  }
  if (_metaData->devMessage != NULL) {
    status = xmlTextWriterStartElementNS(writer, NULL, BAD_CAST "devMessage", NULL);
    if (status < 0) {
#if DEBUG_ENUNCIATE
      printf("Failed to write start element {}devMessage. status: %i\n", status);
#endif
      return status;
    }
    totalBytes += status;
#if DEBUG_ENUNCIATE > 1
    printf("writing type {http://www.w3.org/2001/XMLSchema}string for element {}devMessage...\n");
#endif
    status = xmlTextWriterWriteXsStringType(writer, (_metaData->devMessage));
    if (status < 0) {
#if DEBUG_ENUNCIATE
      printf("Failed to write type {http://www.w3.org/2001/XMLSchema}string for element {}devMessage. status: %i\n", status);
#endif
      return status;
    }
    totalBytes += status;

    status = xmlTextWriterEndElement(writer);
    if (status < 0) {
#if DEBUG_ENUNCIATE
      printf("Failed to write end element {}devMessage. status: %i\n", status);
#endif
      return status;
    }
    totalBytes += status;
  }
  if (_metaData->metaData != NULL) {
    status = xmlTextWriterStartElementNS(writer, NULL, BAD_CAST "metaData", NULL);
    if (status < 0) {
#if DEBUG_ENUNCIATE
      printf("Failed to write start element {}metaData. status: %i\n", status);
#endif
      return status;
    }
    totalBytes += status;
#if DEBUG_ENUNCIATE > 1
    printf("writing type {http://www.w3.org/2001/XMLSchema}anyType for element {}metaData...\n");
#endif
    status = xmlTextWriterWriteXsAnyTypeType(writer, (_metaData->metaData));
    if (status < 0) {
#if DEBUG_ENUNCIATE
      printf("Failed to write type {http://www.w3.org/2001/XMLSchema}anyType for element {}metaData. status: %i\n", status);
#endif
      return status;
    }
    totalBytes += status;

    status = xmlTextWriterEndElement(writer);
    if (status < 0) {
#if DEBUG_ENUNCIATE
      printf("Failed to write end element {}metaData. status: %i\n", status);
#endif
      return status;
    }
    totalBytes += status;
  }

  return totalBytes;
}

/**
 * Frees the elements of a MetaData.
 *
 * @param _metaData The MetaData to free.
 */
static void freeNs0MetaDataType(struct EasyMoney_ns0_metaData *_metaData) {
  int i;
  if (_metaData->message != NULL) {
#if DEBUG_ENUNCIATE > 1
    printf("Freeing type of accessor message of type EasyMoney_ns0_metaData...\n");
#endif
    freeXsStringType(_metaData->message);
#if DEBUG_ENUNCIATE > 1
    printf("Freeing accessor message of type EasyMoney_ns0_metaData...\n");
#endif
    free(_metaData->message);
  }
  if (_metaData->status != NULL) {
#if DEBUG_ENUNCIATE > 1
    printf("Freeing type of accessor status of type EasyMoney_ns0_metaData...\n");
#endif
    freeNs0StatusType(_metaData->status);
#if DEBUG_ENUNCIATE > 1
    printf("Freeing accessor status of type EasyMoney_ns0_metaData...\n");
#endif
    free(_metaData->status);
  }
  if (_metaData->devMessage != NULL) {
#if DEBUG_ENUNCIATE > 1
    printf("Freeing type of accessor devMessage of type EasyMoney_ns0_metaData...\n");
#endif
    freeXsStringType(_metaData->devMessage);
#if DEBUG_ENUNCIATE > 1
    printf("Freeing accessor devMessage of type EasyMoney_ns0_metaData...\n");
#endif
    free(_metaData->devMessage);
  }
  if (_metaData->metaData != NULL) {
#if DEBUG_ENUNCIATE > 1
    printf("Freeing type of accessor metaData of type EasyMoney_ns0_metaData...\n");
#endif
    freeXsAnyTypeType(_metaData->metaData);
#if DEBUG_ENUNCIATE > 1
    printf("Freeing accessor metaData of type EasyMoney_ns0_metaData...\n");
#endif
    free(_metaData->metaData);
  }
}
#endif /* DEF_EasyMoney_ns0_metaData_M */
#ifndef DEF_EasyMoney_ns0_response_M
#define DEF_EasyMoney_ns0_response_M

/**
 * Reads a Response from XML. The reader is assumed to be at the start element.
 *
 * @return the Response, or NULL in case of error.
 */
static struct EasyMoney_ns0_response *xmlTextReaderReadNs0ResponseType(xmlTextReaderPtr reader) {
  int status, depth;
  void *_child_accessor;
  struct EasyMoney_ns0_response *_response = calloc(1, sizeof(struct EasyMoney_ns0_response));



  if (xmlTextReaderIsEmptyElement(reader) == 0) {
    depth = xmlTextReaderDepth(reader);//track the depth.
    status = xmlTextReaderAdvanceToNextStartOrEndElement(reader);

    while (xmlTextReaderDepth(reader) > depth) {
      if (status < 1) {
        //panic: XML read error.
#if DEBUG_ENUNCIATE
        printf("Failure to advance to next child element.\n");
#endif
        freeNs0ResponseType(_response);
        free(_response);
        return NULL;
      }
      else if (xmlTextReaderNodeType(reader) == XML_READER_TYPE_ELEMENT
        && xmlStrcmp(BAD_CAST "data", xmlTextReaderConstLocalName(reader)) == 0
        && xmlTextReaderConstNamespaceUri(reader) == NULL) {

#if DEBUG_ENUNCIATE > 1
        printf("Attempting to read choice {}data of type {http://www.w3.org/2001/XMLSchema}anyType.\n");
#endif
        _child_accessor = xmlTextReaderReadXsAnyTypeType(reader);
        if (_child_accessor == NULL) {
#if DEBUG_ENUNCIATE
          printf("Failed to read choice {}data of type {http://www.w3.org/2001/XMLSchema}anyType.\n");
#endif
          //panic: unable to read the child element for some reason.
          freeNs0ResponseType(_response);
          free(_response);
          return NULL;
        }

        _response->data = ((struct xmlBasicNode*)_child_accessor);
        status = xmlTextReaderAdvanceToNextStartOrEndElement(reader);
      }
      else if (xmlTextReaderNodeType(reader) == XML_READER_TYPE_ELEMENT
        && xmlStrcmp(BAD_CAST "meta", xmlTextReaderConstLocalName(reader)) == 0
        && xmlTextReaderConstNamespaceUri(reader) == NULL) {

#if DEBUG_ENUNCIATE > 1
        printf("Attempting to read choice {}meta of type {}metaData.\n");
#endif
        _child_accessor = xmlTextReaderReadNs0MetaDataType(reader);
        if (_child_accessor == NULL) {
#if DEBUG_ENUNCIATE
          printf("Failed to read choice {}meta of type {}metaData.\n");
#endif
          //panic: unable to read the child element for some reason.
          freeNs0ResponseType(_response);
          free(_response);
          return NULL;
        }

        _response->meta = ((struct EasyMoney_ns0_metaData*)_child_accessor);
        status = xmlTextReaderAdvanceToNextStartOrEndElement(reader);
      }
      else {
#if DEBUG_ENUNCIATE > 1
        if (xmlTextReaderConstNamespaceUri(reader) == NULL) {
          printf("unknown child element {}%s for type {}response.  Skipping...\n",  xmlTextReaderConstLocalName(reader));
        }
        else {
          printf("unknown child element {%s}%s for type {}response. Skipping...\n", xmlTextReaderConstNamespaceUri(reader), xmlTextReaderConstLocalName(reader));
        }
#endif
        status = xmlTextReaderSkipElement(reader);
      }
    }
  }

  return _response;
}

/**
 * Writes a Response to XML.
 *
 * @param writer The XML writer.
 * @param _response The Response to write.
 * @return The total bytes written, or -1 on error;
 */
static int xmlTextWriterWriteNs0ResponseType(xmlTextWriterPtr writer, struct EasyMoney_ns0_response *_response) {
  int status, totalBytes = 0, i;
  xmlChar *binaryData;
  if (_response->data != NULL) {
    status = xmlTextWriterStartElementNS(writer, NULL, BAD_CAST "data", NULL);
    if (status < 0) {
#if DEBUG_ENUNCIATE
      printf("Failed to write start element {}data. status: %i\n", status);
#endif
      return status;
    }
    totalBytes += status;
#if DEBUG_ENUNCIATE > 1
    printf("writing type {http://www.w3.org/2001/XMLSchema}anyType for element {}data...\n");
#endif
    status = xmlTextWriterWriteXsAnyTypeType(writer, (_response->data));
    if (status < 0) {
#if DEBUG_ENUNCIATE
      printf("Failed to write type {http://www.w3.org/2001/XMLSchema}anyType for element {}data. status: %i\n", status);
#endif
      return status;
    }
    totalBytes += status;

    status = xmlTextWriterEndElement(writer);
    if (status < 0) {
#if DEBUG_ENUNCIATE
      printf("Failed to write end element {}data. status: %i\n", status);
#endif
      return status;
    }
    totalBytes += status;
  }
  if (_response->meta != NULL) {
    status = xmlTextWriterStartElementNS(writer, NULL, BAD_CAST "meta", NULL);
    if (status < 0) {
#if DEBUG_ENUNCIATE
      printf("Failed to write start element {}meta. status: %i\n", status);
#endif
      return status;
    }
    totalBytes += status;
#if DEBUG_ENUNCIATE > 1
    printf("writing type {}metaData for element {}meta...\n");
#endif
    status = xmlTextWriterWriteNs0MetaDataType(writer, (_response->meta));
    if (status < 0) {
#if DEBUG_ENUNCIATE
      printf("Failed to write type {}metaData for element {}meta. status: %i\n", status);
#endif
      return status;
    }
    totalBytes += status;

    status = xmlTextWriterEndElement(writer);
    if (status < 0) {
#if DEBUG_ENUNCIATE
      printf("Failed to write end element {}meta. status: %i\n", status);
#endif
      return status;
    }
    totalBytes += status;
  }

  return totalBytes;
}

/**
 * Frees the elements of a Response.
 *
 * @param _response The Response to free.
 */
static void freeNs0ResponseType(struct EasyMoney_ns0_response *_response) {
  int i;
  if (_response->data != NULL) {
#if DEBUG_ENUNCIATE > 1
    printf("Freeing type of accessor data of type EasyMoney_ns0_response...\n");
#endif
    freeXsAnyTypeType(_response->data);
#if DEBUG_ENUNCIATE > 1
    printf("Freeing accessor data of type EasyMoney_ns0_response...\n");
#endif
    free(_response->data);
  }
  if (_response->meta != NULL) {
#if DEBUG_ENUNCIATE > 1
    printf("Freeing type of accessor meta of type EasyMoney_ns0_response...\n");
#endif
    freeNs0MetaDataType(_response->meta);
#if DEBUG_ENUNCIATE > 1
    printf("Freeing accessor meta of type EasyMoney_ns0_response...\n");
#endif
    free(_response->meta);
  }
}
#endif /* DEF_EasyMoney_ns0_response_M */
