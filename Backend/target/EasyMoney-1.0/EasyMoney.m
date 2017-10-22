#import "EasyMoney.h"
#ifndef DEF_EASYMONEYNS0MetaData_M
#define DEF_EASYMONEYNS0MetaData_M

/**
 *  modelo contenedor de los metadatos de una respuesta
 *  @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
@implementation EASYMONEYNS0MetaData

/**
 * (no documentation provided)
 */
- (NSString *) message
{
  return _message;
}

/**
 * (no documentation provided)
 */
- (void) setMessage: (NSString *) newMessage
{
  [newMessage retain];
  [_message release];
  _message = newMessage;
}

/**
 * (no documentation provided)
 */
- (enum EASYMONEYNS0Status *) status
{
  return _status;
}

/**
 * (no documentation provided)
 */
- (void) setStatus: (enum EASYMONEYNS0Status *) newStatus
{
  if (_status != NULL) {
    free(_status);
  }
  _status = newStatus;
}

/**
 * (no documentation provided)
 */
- (NSString *) devMessage
{
  return _devMessage;
}

/**
 * (no documentation provided)
 */
- (void) setDevMessage: (NSString *) newDevMessage
{
  [newDevMessage retain];
  [_devMessage release];
  _devMessage = newDevMessage;
}

/**
 * (no documentation provided)
 */
- (NSObject *) metaData
{
  return _metaData;
}

/**
 * (no documentation provided)
 */
- (void) setMetaData: (NSObject *) newMetaData
{
  [newMetaData retain];
  [_metaData release];
  _metaData = newMetaData;
}

- (void) dealloc
{
  [self setMessage: nil];
  [self setStatus: NULL];
  [self setDevMessage: nil];
  [self setMetaData: nil];
  [super dealloc];
}
@end /* implementation EASYMONEYNS0MetaData */

/**
 * Internal, private interface for JAXB reading and writing.
 */
@interface EASYMONEYNS0MetaData (JAXB) <JAXBReading, JAXBWriting, JAXBType>

@end /*interface EASYMONEYNS0MetaData (JAXB)*/

/**
 * Internal, private implementation for JAXB reading and writing.
 */
@implementation EASYMONEYNS0MetaData (JAXB)

/**
 * Read an instance of EASYMONEYNS0MetaData from an XML reader.
 *
 * @param reader The reader.
 * @return An instance of EASYMONEYNS0MetaData defined by the XML reader.
 */
+ (id<JAXBType>) readXMLType: (xmlTextReaderPtr) reader
{
  EASYMONEYNS0MetaData *_eASYMONEYNS0MetaData = [[EASYMONEYNS0MetaData alloc] init];
  NS_DURING
  {
    [_eASYMONEYNS0MetaData initWithReader: reader];
  }
  NS_HANDLER
  {
    _eASYMONEYNS0MetaData = nil;
    [localException raise];
  }
  NS_ENDHANDLER

  [_eASYMONEYNS0MetaData autorelease];
  return _eASYMONEYNS0MetaData;
}

/**
 * Initialize this instance of EASYMONEYNS0MetaData according to
 * the XML being read from the reader.
 *
 * @param reader The reader.
 */
- (id) initWithReader: (xmlTextReaderPtr) reader
{
  return [super initWithReader: reader];
}

/**
 * Write the XML for this instance of EASYMONEYNS0MetaData to the writer.
 * Note that since we're only writing the XML type,
 * No start/end element will be written.
 *
 * @param reader The reader.
 */
- (void) writeXMLType: (xmlTextWriterPtr) writer
{
  [super writeXMLType:writer];
}

//documentation inherited.
- (BOOL) readJAXBAttribute: (xmlTextReaderPtr) reader
{
  void *_child_accessor;

  if ([super readJAXBAttribute: reader]) {
    return YES;
  }

  return NO;
}

//documentation inherited.
- (BOOL) readJAXBValue: (xmlTextReaderPtr) reader
{
  return [super readJAXBValue: reader];
}

//documentation inherited.
- (BOOL) readJAXBChildElement: (xmlTextReaderPtr) reader
{
  id __child;
  void *_child_accessor;
  int status, depth;

  if ([super readJAXBChildElement: reader]) {
    return YES;
  }
  if (xmlTextReaderNodeType(reader) == XML_READER_TYPE_ELEMENT
    && xmlStrcmp(BAD_CAST "message", xmlTextReaderConstLocalName(reader)) == 0
    && xmlTextReaderConstNamespaceUri(reader) == NULL) {

#if DEBUG_ENUNCIATE > 1
    NSLog(@"Attempting to read choice {}message of type {http://www.w3.org/2001/XMLSchema}string.");
#endif
    __child = [NSString readXMLType: reader];
#if DEBUG_ENUNCIATE > 1
    NSLog(@"successfully read choice {}message of type {http://www.w3.org/2001/XMLSchema}string.");
#endif

    [self setMessage: __child];
    return YES;
  } //end "if choice"


  if (xmlTextReaderNodeType(reader) == XML_READER_TYPE_ELEMENT
    && xmlStrcmp(BAD_CAST "status", xmlTextReaderConstLocalName(reader)) == 0
    && xmlTextReaderConstNamespaceUri(reader) == NULL) {

    _child_accessor = xmlTextReaderReadEASYMONEYNS0StatusType(reader);
    if (_child_accessor == NULL) {
      //panic: unable to return the value for some reason.
      [NSException raise: @"XMLReadError"
                   format: @"Error reading element value."];
    }
    [self setStatus: ((enum EASYMONEYNS0Status*) _child_accessor)];
    return YES;
  }
  if (xmlTextReaderNodeType(reader) == XML_READER_TYPE_ELEMENT
    && xmlStrcmp(BAD_CAST "devMessage", xmlTextReaderConstLocalName(reader)) == 0
    && xmlTextReaderConstNamespaceUri(reader) == NULL) {

#if DEBUG_ENUNCIATE > 1
    NSLog(@"Attempting to read choice {}devMessage of type {http://www.w3.org/2001/XMLSchema}string.");
#endif
    __child = [NSString readXMLType: reader];
#if DEBUG_ENUNCIATE > 1
    NSLog(@"successfully read choice {}devMessage of type {http://www.w3.org/2001/XMLSchema}string.");
#endif

    [self setDevMessage: __child];
    return YES;
  } //end "if choice"

  if (xmlTextReaderNodeType(reader) == XML_READER_TYPE_ELEMENT
    && xmlStrcmp(BAD_CAST "metaData", xmlTextReaderConstLocalName(reader)) == 0
    && xmlTextReaderConstNamespaceUri(reader) == NULL) {

#if DEBUG_ENUNCIATE > 1
    NSLog(@"Attempting to read choice {}metaData of type {http://www.w3.org/2001/XMLSchema}anyType.");
#endif
    __child = [NSObject readXMLType: reader];
#if DEBUG_ENUNCIATE > 1
    NSLog(@"successfully read choice {}metaData of type {http://www.w3.org/2001/XMLSchema}anyType.");
#endif

    [self setMetaData: __child];
    return YES;
  } //end "if choice"


  return NO;
}

//documentation inherited.
- (int) readUnknownJAXBChildElement: (xmlTextReaderPtr) reader
{
  return [super readUnknownJAXBChildElement: reader];
}

//documentation inherited.
- (void) readUnknownJAXBAttribute: (xmlTextReaderPtr) reader
{
  [super readUnknownJAXBAttribute: reader];
}

//documentation inherited.
- (void) writeJAXBAttributes: (xmlTextWriterPtr) writer
{
  int status;

  [super writeJAXBAttributes: writer];

}

//documentation inherited.
- (void) writeJAXBValue: (xmlTextWriterPtr) writer
{
  [super writeJAXBValue: writer];
}

/**
 * Method for writing the child elements.
 *
 * @param writer The writer.
 */
- (void) writeJAXBChildElements: (xmlTextWriterPtr) writer
{
  int status;
  id __item;
  id __item_copy;
  NSEnumerator *__enumerator;

  [super writeJAXBChildElements: writer];

  if ([self message]) {
    status = xmlTextWriterStartElementNS(writer, NULL, BAD_CAST "message", NULL);
    if (status < 0) {
      [NSException raise: @"XMLWriteError"
                   format: @"Error writing start child element {}message."];
    }

#if DEBUG_ENUNCIATE > 1
    NSLog(@"writing element {}message...");
#endif
    [[self message] writeXMLType: writer];
#if DEBUG_ENUNCIATE > 1
    NSLog(@"successfully wrote element {}message...");
#endif

    status = xmlTextWriterEndElement(writer);
    if (status < 0) {
      [NSException raise: @"XMLWriteError"
                   format: @"Error writing end child element {}message."];
    }
  }
  if ([self status] != NULL) {
    status = xmlTextWriterStartElementNS(writer, NULL, BAD_CAST "status", NULL);
    if (status < 0) {
      [NSException raise: @"XMLWriteError"
                   format: @"Error writing start child element {}status."];
    }

#if DEBUG_ENUNCIATE > 1
    NSLog(@"writing element {}status...");
#endif
    status = xmlTextWriterWriteEASYMONEYNS0StatusType(writer, [self status]);
#if DEBUG_ENUNCIATE > 1
    NSLog(@"successfully wrote element {}status...");
#endif
    if (status < 0) {
      [NSException raise: @"XMLWriteError"
                   format: @"Error writing child element {}status."];
    }

    status = xmlTextWriterEndElement(writer);
    if (status < 0) {
      [NSException raise: @"XMLWriteError"
                   format: @"Error writing end child element {}status."];
    }
  }
  if ([self devMessage]) {
    status = xmlTextWriterStartElementNS(writer, NULL, BAD_CAST "devMessage", NULL);
    if (status < 0) {
      [NSException raise: @"XMLWriteError"
                   format: @"Error writing start child element {}devMessage."];
    }

#if DEBUG_ENUNCIATE > 1
    NSLog(@"writing element {}devMessage...");
#endif
    [[self devMessage] writeXMLType: writer];
#if DEBUG_ENUNCIATE > 1
    NSLog(@"successfully wrote element {}devMessage...");
#endif

    status = xmlTextWriterEndElement(writer);
    if (status < 0) {
      [NSException raise: @"XMLWriteError"
                   format: @"Error writing end child element {}devMessage."];
    }
  }
  if ([self metaData]) {
    status = xmlTextWriterStartElementNS(writer, NULL, BAD_CAST "metaData", NULL);
    if (status < 0) {
      [NSException raise: @"XMLWriteError"
                   format: @"Error writing start child element {}metaData."];
    }

#if DEBUG_ENUNCIATE > 1
    NSLog(@"writing element {}metaData...");
#endif
    [[self metaData] writeXMLType: writer];
#if DEBUG_ENUNCIATE > 1
    NSLog(@"successfully wrote element {}metaData...");
#endif

    status = xmlTextWriterEndElement(writer);
    if (status < 0) {
      [NSException raise: @"XMLWriteError"
                   format: @"Error writing end child element {}metaData."];
    }
  }
}
@end /* implementation EASYMONEYNS0MetaData (JAXB) */

#endif /* DEF_EASYMONEYNS0MetaData_M */
#ifndef DEF_EASYMONEYNS0Response_M
#define DEF_EASYMONEYNS0Response_M

/**
 *  modelo de respuesta generico para servicios
 *  @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
@implementation EASYMONEYNS0Response

/**
 * (no documentation provided)
 */
- (NSObject *) data
{
  return _data;
}

/**
 * (no documentation provided)
 */
- (void) setData: (NSObject *) newData
{
  [newData retain];
  [_data release];
  _data = newData;
}

/**
 * (no documentation provided)
 */
- (EASYMONEYNS0MetaData *) meta
{
  return _meta;
}

/**
 * (no documentation provided)
 */
- (void) setMeta: (EASYMONEYNS0MetaData *) newMeta
{
  [newMeta retain];
  [_meta release];
  _meta = newMeta;
}

- (void) dealloc
{
  [self setData: nil];
  [self setMeta: nil];
  [super dealloc];
}
@end /* implementation EASYMONEYNS0Response */

/**
 * Internal, private interface for JAXB reading and writing.
 */
@interface EASYMONEYNS0Response (JAXB) <JAXBReading, JAXBWriting, JAXBType>

@end /*interface EASYMONEYNS0Response (JAXB)*/

/**
 * Internal, private implementation for JAXB reading and writing.
 */
@implementation EASYMONEYNS0Response (JAXB)

/**
 * Read an instance of EASYMONEYNS0Response from an XML reader.
 *
 * @param reader The reader.
 * @return An instance of EASYMONEYNS0Response defined by the XML reader.
 */
+ (id<JAXBType>) readXMLType: (xmlTextReaderPtr) reader
{
  EASYMONEYNS0Response *_eASYMONEYNS0Response = [[EASYMONEYNS0Response alloc] init];
  NS_DURING
  {
    [_eASYMONEYNS0Response initWithReader: reader];
  }
  NS_HANDLER
  {
    _eASYMONEYNS0Response = nil;
    [localException raise];
  }
  NS_ENDHANDLER

  [_eASYMONEYNS0Response autorelease];
  return _eASYMONEYNS0Response;
}

/**
 * Initialize this instance of EASYMONEYNS0Response according to
 * the XML being read from the reader.
 *
 * @param reader The reader.
 */
- (id) initWithReader: (xmlTextReaderPtr) reader
{
  return [super initWithReader: reader];
}

/**
 * Write the XML for this instance of EASYMONEYNS0Response to the writer.
 * Note that since we're only writing the XML type,
 * No start/end element will be written.
 *
 * @param reader The reader.
 */
- (void) writeXMLType: (xmlTextWriterPtr) writer
{
  [super writeXMLType:writer];
}

//documentation inherited.
- (BOOL) readJAXBAttribute: (xmlTextReaderPtr) reader
{
  void *_child_accessor;

  if ([super readJAXBAttribute: reader]) {
    return YES;
  }

  return NO;
}

//documentation inherited.
- (BOOL) readJAXBValue: (xmlTextReaderPtr) reader
{
  return [super readJAXBValue: reader];
}

//documentation inherited.
- (BOOL) readJAXBChildElement: (xmlTextReaderPtr) reader
{
  id __child;
  void *_child_accessor;
  int status, depth;

  if ([super readJAXBChildElement: reader]) {
    return YES;
  }
  if (xmlTextReaderNodeType(reader) == XML_READER_TYPE_ELEMENT
    && xmlStrcmp(BAD_CAST "data", xmlTextReaderConstLocalName(reader)) == 0
    && xmlTextReaderConstNamespaceUri(reader) == NULL) {

#if DEBUG_ENUNCIATE > 1
    NSLog(@"Attempting to read choice {}data of type {http://www.w3.org/2001/XMLSchema}anyType.");
#endif
    __child = [NSObject readXMLType: reader];
#if DEBUG_ENUNCIATE > 1
    NSLog(@"successfully read choice {}data of type {http://www.w3.org/2001/XMLSchema}anyType.");
#endif

    [self setData: __child];
    return YES;
  } //end "if choice"

  if (xmlTextReaderNodeType(reader) == XML_READER_TYPE_ELEMENT
    && xmlStrcmp(BAD_CAST "meta", xmlTextReaderConstLocalName(reader)) == 0
    && xmlTextReaderConstNamespaceUri(reader) == NULL) {

#if DEBUG_ENUNCIATE > 1
    NSLog(@"Attempting to read choice {}meta of type {}metaData.");
#endif
    __child = [EASYMONEYNS0MetaData readXMLType: reader];
#if DEBUG_ENUNCIATE > 1
    NSLog(@"successfully read choice {}meta of type {}metaData.");
#endif

    [self setMeta: __child];
    return YES;
  } //end "if choice"


  return NO;
}

//documentation inherited.
- (int) readUnknownJAXBChildElement: (xmlTextReaderPtr) reader
{
  return [super readUnknownJAXBChildElement: reader];
}

//documentation inherited.
- (void) readUnknownJAXBAttribute: (xmlTextReaderPtr) reader
{
  [super readUnknownJAXBAttribute: reader];
}

//documentation inherited.
- (void) writeJAXBAttributes: (xmlTextWriterPtr) writer
{
  int status;

  [super writeJAXBAttributes: writer];

}

//documentation inherited.
- (void) writeJAXBValue: (xmlTextWriterPtr) writer
{
  [super writeJAXBValue: writer];
}

/**
 * Method for writing the child elements.
 *
 * @param writer The writer.
 */
- (void) writeJAXBChildElements: (xmlTextWriterPtr) writer
{
  int status;
  id __item;
  id __item_copy;
  NSEnumerator *__enumerator;

  [super writeJAXBChildElements: writer];

  if ([self data]) {
    status = xmlTextWriterStartElementNS(writer, NULL, BAD_CAST "data", NULL);
    if (status < 0) {
      [NSException raise: @"XMLWriteError"
                   format: @"Error writing start child element {}data."];
    }

#if DEBUG_ENUNCIATE > 1
    NSLog(@"writing element {}data...");
#endif
    [[self data] writeXMLType: writer];
#if DEBUG_ENUNCIATE > 1
    NSLog(@"successfully wrote element {}data...");
#endif

    status = xmlTextWriterEndElement(writer);
    if (status < 0) {
      [NSException raise: @"XMLWriteError"
                   format: @"Error writing end child element {}data."];
    }
  }
  if ([self meta]) {
    status = xmlTextWriterStartElementNS(writer, NULL, BAD_CAST "meta", NULL);
    if (status < 0) {
      [NSException raise: @"XMLWriteError"
                   format: @"Error writing start child element {}meta."];
    }

#if DEBUG_ENUNCIATE > 1
    NSLog(@"writing element {}meta...");
#endif
    [[self meta] writeXMLType: writer];
#if DEBUG_ENUNCIATE > 1
    NSLog(@"successfully wrote element {}meta...");
#endif

    status = xmlTextWriterEndElement(writer);
    if (status < 0) {
      [NSException raise: @"XMLWriteError"
                   format: @"Error writing end child element {}meta."];
    }
  }
}
@end /* implementation EASYMONEYNS0Response (JAXB) */

#endif /* DEF_EASYMONEYNS0Response_M */
#ifndef DEF_EASYMONEYNS0Status_M
#define DEF_EASYMONEYNS0Status_M

/**
 * Reads a Status from XML. The reader is assumed to be at the start element.
 *
 * @param reader The XML reader.
 * @return The Status, or NULL if unable to be read.
 */
static enum EASYMONEYNS0Status *xmlTextReaderReadEASYMONEYNS0StatusType(xmlTextReaderPtr reader)
{
  xmlChar *enumValue = xmlTextReaderReadEntireNodeValue(reader);
  enum EASYMONEYNS0Status *value = calloc(1, sizeof(enum EASYMONEYNS0Status));
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
    NSLog(@"Attempt to read enum value failed: %s doesn't match an enum value.", enumValue);
#endif
  }
#if DEBUG_ENUNCIATE
  else {
    NSLog(@"Attempt to read enum value failed: NULL value.");
  }
#endif

  return NULL;
}

/**
 * Utility method for getting the enum value for a string.
 *
 * @param _status The string to format.
 * @return The enum value or NULL on error.
 */
enum EASYMONEYNS0Status *formatStringToEASYMONEYNS0StatusType(NSString *_status)
{
  enum EASYMONEYNS0Status *value = calloc(1, sizeof(enum EASYMONEYNS0Status));
  if ([@"OK" isEqualToString:_status]) {
    *value = EASYMONEY_NS0_STATUS_OK;
  }
  else if ([@"ERROR" isEqualToString:_status]) {
    *value = EASYMONEY_NS0_STATUS_ERROR;
  }
  else if ([@"WARNING" isEqualToString:_status]) {
    *value = EASYMONEY_NS0_STATUS_WARNING;
  }
  else if ([@"ACCES_DENIED" isEqualToString:_status]) {
    *value = EASYMONEY_NS0_STATUS_ACCES_DENIED;
  }
  else if ([@"INVALID_PARAM" isEqualToString:_status]) {
    *value = EASYMONEY_NS0_STATUS_INVALID_PARAM;
  }
  else if ([@"PARCIAL_ACCESS" isEqualToString:_status]) {
    *value = EASYMONEY_NS0_STATUS_PARCIAL_ACCESS;
  }
  else{
#if DEBUG_ENUNCIATE
  NSLog(@"Attempt to read enum value failed: %s doesn't match an enum value.", [_status UTF8String]);
#endif
    value = NULL;
  }
  return value;
}

/**
 * Writes a Status to XML.
 *
 * @param writer The XML writer.
 * @param _status The Status to write.
 * @return The bytes written (may be 0 in case of buffering) or -1 in case of error.
 */
static int xmlTextWriterWriteEASYMONEYNS0StatusType(xmlTextWriterPtr writer, enum EASYMONEYNS0Status *_status)
{
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
  NSLog(@"Unable to write enum value (no valid value found).");
#endif
  return -1;
}

/**
 * Utility method for getting the string value of Status.
 *
 * @param _status The Status to format.
 * @return The string value or NULL on error.
 */
static NSString *formatEASYMONEYNS0StatusTypeToString(enum EASYMONEYNS0Status *_status)
{
  switch (*_status) {
    case EASYMONEY_NS0_STATUS_OK:
      return @"OK";
    case EASYMONEY_NS0_STATUS_ERROR:
      return @"ERROR";
    case EASYMONEY_NS0_STATUS_WARNING:
      return @"WARNING";
    case EASYMONEY_NS0_STATUS_ACCES_DENIED:
      return @"ACCES_DENIED";
    case EASYMONEY_NS0_STATUS_INVALID_PARAM:
      return @"INVALID_PARAM";
    case EASYMONEY_NS0_STATUS_PARCIAL_ACCESS:
      return @"PARCIAL_ACCESS";
    default:
      return NULL;
  }

  return NULL;
}
#endif /* DEF_EASYMONEYNS0Status_M */
#ifndef DEF_EASYMONEYNS0ModelLogin_M
#define DEF_EASYMONEYNS0ModelLogin_M

/**
 * 
 *  @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
@implementation EASYMONEYNS0ModelLogin

/**
 * (no documentation provided)
 */
- (NSString *) user
{
  return _user;
}

/**
 * (no documentation provided)
 */
- (void) setUser: (NSString *) newUser
{
  [newUser retain];
  [_user release];
  _user = newUser;
}

/**
 * (no documentation provided)
 */
- (NSString *) pass
{
  return _pass;
}

/**
 * (no documentation provided)
 */
- (void) setPass: (NSString *) newPass
{
  [newPass retain];
  [_pass release];
  _pass = newPass;
}

- (void) dealloc
{
  [self setUser: nil];
  [self setPass: nil];
  [super dealloc];
}
@end /* implementation EASYMONEYNS0ModelLogin */

/**
 * Internal, private interface for JAXB reading and writing.
 */
@interface EASYMONEYNS0ModelLogin (JAXB) <JAXBReading, JAXBWriting, JAXBType>

@end /*interface EASYMONEYNS0ModelLogin (JAXB)*/

/**
 * Internal, private implementation for JAXB reading and writing.
 */
@implementation EASYMONEYNS0ModelLogin (JAXB)

/**
 * Read an instance of EASYMONEYNS0ModelLogin from an XML reader.
 *
 * @param reader The reader.
 * @return An instance of EASYMONEYNS0ModelLogin defined by the XML reader.
 */
+ (id<JAXBType>) readXMLType: (xmlTextReaderPtr) reader
{
  EASYMONEYNS0ModelLogin *_eASYMONEYNS0ModelLogin = [[EASYMONEYNS0ModelLogin alloc] init];
  NS_DURING
  {
    [_eASYMONEYNS0ModelLogin initWithReader: reader];
  }
  NS_HANDLER
  {
    _eASYMONEYNS0ModelLogin = nil;
    [localException raise];
  }
  NS_ENDHANDLER

  [_eASYMONEYNS0ModelLogin autorelease];
  return _eASYMONEYNS0ModelLogin;
}

/**
 * Initialize this instance of EASYMONEYNS0ModelLogin according to
 * the XML being read from the reader.
 *
 * @param reader The reader.
 */
- (id) initWithReader: (xmlTextReaderPtr) reader
{
  return [super initWithReader: reader];
}

/**
 * Write the XML for this instance of EASYMONEYNS0ModelLogin to the writer.
 * Note that since we're only writing the XML type,
 * No start/end element will be written.
 *
 * @param reader The reader.
 */
- (void) writeXMLType: (xmlTextWriterPtr) writer
{
  [super writeXMLType:writer];
}

//documentation inherited.
- (BOOL) readJAXBAttribute: (xmlTextReaderPtr) reader
{
  void *_child_accessor;

  if ([super readJAXBAttribute: reader]) {
    return YES;
  }

  return NO;
}

//documentation inherited.
- (BOOL) readJAXBValue: (xmlTextReaderPtr) reader
{
  return [super readJAXBValue: reader];
}

//documentation inherited.
- (BOOL) readJAXBChildElement: (xmlTextReaderPtr) reader
{
  id __child;
  void *_child_accessor;
  int status, depth;

  if ([super readJAXBChildElement: reader]) {
    return YES;
  }
  if (xmlTextReaderNodeType(reader) == XML_READER_TYPE_ELEMENT
    && xmlStrcmp(BAD_CAST "user", xmlTextReaderConstLocalName(reader)) == 0
    && xmlTextReaderConstNamespaceUri(reader) == NULL) {

#if DEBUG_ENUNCIATE > 1
    NSLog(@"Attempting to read choice {}user of type {http://www.w3.org/2001/XMLSchema}string.");
#endif
    __child = [NSString readXMLType: reader];
#if DEBUG_ENUNCIATE > 1
    NSLog(@"successfully read choice {}user of type {http://www.w3.org/2001/XMLSchema}string.");
#endif

    [self setUser: __child];
    return YES;
  } //end "if choice"

  if (xmlTextReaderNodeType(reader) == XML_READER_TYPE_ELEMENT
    && xmlStrcmp(BAD_CAST "pass", xmlTextReaderConstLocalName(reader)) == 0
    && xmlTextReaderConstNamespaceUri(reader) == NULL) {

#if DEBUG_ENUNCIATE > 1
    NSLog(@"Attempting to read choice {}pass of type {http://www.w3.org/2001/XMLSchema}string.");
#endif
    __child = [NSString readXMLType: reader];
#if DEBUG_ENUNCIATE > 1
    NSLog(@"successfully read choice {}pass of type {http://www.w3.org/2001/XMLSchema}string.");
#endif

    [self setPass: __child];
    return YES;
  } //end "if choice"


  return NO;
}

//documentation inherited.
- (int) readUnknownJAXBChildElement: (xmlTextReaderPtr) reader
{
  return [super readUnknownJAXBChildElement: reader];
}

//documentation inherited.
- (void) readUnknownJAXBAttribute: (xmlTextReaderPtr) reader
{
  [super readUnknownJAXBAttribute: reader];
}

//documentation inherited.
- (void) writeJAXBAttributes: (xmlTextWriterPtr) writer
{
  int status;

  [super writeJAXBAttributes: writer];

}

//documentation inherited.
- (void) writeJAXBValue: (xmlTextWriterPtr) writer
{
  [super writeJAXBValue: writer];
}

/**
 * Method for writing the child elements.
 *
 * @param writer The writer.
 */
- (void) writeJAXBChildElements: (xmlTextWriterPtr) writer
{
  int status;
  id __item;
  id __item_copy;
  NSEnumerator *__enumerator;

  [super writeJAXBChildElements: writer];

  if ([self user]) {
    status = xmlTextWriterStartElementNS(writer, NULL, BAD_CAST "user", NULL);
    if (status < 0) {
      [NSException raise: @"XMLWriteError"
                   format: @"Error writing start child element {}user."];
    }

#if DEBUG_ENUNCIATE > 1
    NSLog(@"writing element {}user...");
#endif
    [[self user] writeXMLType: writer];
#if DEBUG_ENUNCIATE > 1
    NSLog(@"successfully wrote element {}user...");
#endif

    status = xmlTextWriterEndElement(writer);
    if (status < 0) {
      [NSException raise: @"XMLWriteError"
                   format: @"Error writing end child element {}user."];
    }
  }
  if ([self pass]) {
    status = xmlTextWriterStartElementNS(writer, NULL, BAD_CAST "pass", NULL);
    if (status < 0) {
      [NSException raise: @"XMLWriteError"
                   format: @"Error writing start child element {}pass."];
    }

#if DEBUG_ENUNCIATE > 1
    NSLog(@"writing element {}pass...");
#endif
    [[self pass] writeXMLType: writer];
#if DEBUG_ENUNCIATE > 1
    NSLog(@"successfully wrote element {}pass...");
#endif

    status = xmlTextWriterEndElement(writer);
    if (status < 0) {
      [NSException raise: @"XMLWriteError"
                   format: @"Error writing end child element {}pass."];
    }
  }
}
@end /* implementation EASYMONEYNS0ModelLogin (JAXB) */

#endif /* DEF_EASYMONEYNS0ModelLogin_M */
