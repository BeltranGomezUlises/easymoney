#import "enunciate-common.h"
#ifndef DEF_EASYMONEYNS0Status_H
#define DEF_EASYMONEYNS0Status_H

/**
 *  enumerador de estado de operacion
 *  @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
enum EASYMONEYNS0Status
{

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
static enum EASYMONEYNS0Status *xmlTextReaderReadEASYMONEYNS0StatusType(xmlTextReaderPtr reader);

/**
 * Writes a Status to XML.
 *
 * @param writer The XML writer.
 * @param _status The Status to write.
 * @return The bytes written (may be 0 in case of buffering) or -1 in case of error.
 */
static int xmlTextWriterWriteEASYMONEYNS0StatusType(xmlTextWriterPtr writer, enum EASYMONEYNS0Status *_status);

/**
 * Utility method for getting the enum value for a string.
 *
 * @param _status The string to format.
 * @return The enum value or NULL on error.
 */
static enum EASYMONEYNS0Status *formatStringToEASYMONEYNS0StatusType(NSString *_status);

/**
 * Utility method for getting the string value of Status.
 *
 * @param _status The Status to format.
 * @return The string value or NULL on error.
 */
static NSString *formatEASYMONEYNS0StatusTypeToString(enum EASYMONEYNS0Status *_status);
#endif /* DEF_EASYMONEYNS0Status_H */

@class EASYMONEYNS0MetaData;
@class EASYMONEYNS0Response;
@class EASYMONEYNS0ModelLogin;

#ifndef DEF_EASYMONEYNS0MetaData_H
#define DEF_EASYMONEYNS0MetaData_H

/**
 *  modelo contenedor de los metadatos de una respuesta
 *  @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
@interface EASYMONEYNS0MetaData : NSObject
{
  @private
    NSString *_message;
    enum EASYMONEYNS0Status *_status;
    NSString *_devMessage;
    NSObject *_metaData;
}

/**
 * (no documentation provided)
 */
- (NSString *) message;

/**
 * (no documentation provided)
 */
- (void) setMessage: (NSString *) newMessage;

/**
 * (no documentation provided)
 */
- (enum EASYMONEYNS0Status *) status;

/**
 * (no documentation provided)
 */
- (void) setStatus: (enum EASYMONEYNS0Status *) newStatus;

/**
 * (no documentation provided)
 */
- (NSString *) devMessage;

/**
 * (no documentation provided)
 */
- (void) setDevMessage: (NSString *) newDevMessage;

/**
 * (no documentation provided)
 */
- (NSObject *) metaData;

/**
 * (no documentation provided)
 */
- (void) setMetaData: (NSObject *) newMetaData;
@end /* interface EASYMONEYNS0MetaData */

#endif /* DEF_EASYMONEYNS0MetaData_H */
#ifndef DEF_EASYMONEYNS0Response_H
#define DEF_EASYMONEYNS0Response_H

/**
 *  modelo de respuesta generico para servicios
 *  @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
@interface EASYMONEYNS0Response : NSObject
{
  @private
    NSObject *_data;
    EASYMONEYNS0MetaData *_meta;
}

/**
 * (no documentation provided)
 */
- (NSObject *) data;

/**
 * (no documentation provided)
 */
- (void) setData: (NSObject *) newData;

/**
 * (no documentation provided)
 */
- (EASYMONEYNS0MetaData *) meta;

/**
 * (no documentation provided)
 */
- (void) setMeta: (EASYMONEYNS0MetaData *) newMeta;
@end /* interface EASYMONEYNS0Response */

#endif /* DEF_EASYMONEYNS0Response_H */
#ifndef DEF_EASYMONEYNS0ModelLogin_H
#define DEF_EASYMONEYNS0ModelLogin_H

/**
 * 
 *  @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 */
@interface EASYMONEYNS0ModelLogin : NSObject
{
  @private
    NSString *_user;
    NSString *_pass;
}

/**
 * (no documentation provided)
 */
- (NSString *) user;

/**
 * (no documentation provided)
 */
- (void) setUser: (NSString *) newUser;

/**
 * (no documentation provided)
 */
- (NSString *) pass;

/**
 * (no documentation provided)
 */
- (void) setPass: (NSString *) newPass;
@end /* interface EASYMONEYNS0ModelLogin */

#endif /* DEF_EASYMONEYNS0ModelLogin_H */
